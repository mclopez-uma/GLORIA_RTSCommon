#include "eu_gloria_rt_catalogue_libnova_LibNovaJNI.h"
#include <string.h>
#include <sys/time.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <libnova/libnova.h>
#include <libnova/julian_day.h>
#include <libnova/rise_set.h>
#include <libnova/transform.h>
#include <libnova/solar.h>
#include <libnova/lunar.h>
#include <libnova/mars.h>
#include <libnova/mercury.h>
#include <libnova/neptune.h>
#include <libnova/pluto.h>
#include <libnova/saturn.h>
#include <libnova/uranus.h>
#include <libnova/venus.h>


double getDoubleFromStr(char *target, int initialPos, int length)
{
	char *tmp = malloc(40);
	memset(tmp, 0, 40);
	char *begin = target;
	begin += initialPos;
	strncpy(tmp, begin, length);	
	double result = atof(tmp);
	free(tmp);
	return result;
}

int getIntFromStr(char *target, int initialPos, int length)
{
    char* tmp = (char*)malloc(40);
	memset(tmp, 0, 40);
	char *begin = target;
    begin += initialPos;
    strncpy(tmp, begin, length);
	int result = atoi(tmp);
	free(tmp);
	return result;
}

void print_date (char * title, struct ln_zonedate* date)
{
	printf ("\n/***%s***/\n",title);
	printf (" Year    : %d\n", date->years);
	printf (" Month   : %d\n", date->months);
	printf (" Day     : %d\n", date->days);
	printf (" Hours   : %d\n", date->hours);
	printf (" Minutes : %d\n", date->minutes);
	printf (" Seconds : %f\n", date->seconds);
}

void showInfo (char * title, int circumpolarValue, struct ln_zonedate rise, struct ln_zonedate set, struct ln_zonedate transit, struct ln_equ_posn* equ)
{
        printf ("\n[*********OBJECT::::::::  %s  ****************]\n",title);
        printf (" circumplar==> %d\n", circumpolarValue);
        print_date ("RISE", &rise);
        print_date ("SET", &set);
        print_date ("TRANSIT", &transit);
        printf ("POS-RA : %f\n", equ->ra);
        printf ("POS-DEC: %f\n", equ->dec);

}

/*
 * Class:     eu_gloria_rt_catalogue_libnova_LibNovaJNI
 * Method:    getRTS
 * Signature: (Leu/gloria/rt/catalogue/libnova/LibNovaObserver;Ljava/lang/String;Leu/gloria/rt/catalogue/libnova/LibNovaRTSInfo;Leu/gloria/rt/catalogue/libnova/LibNovaReturnedInfo;)V
 */
JNIEXPORT void JNICALL Java_eu_gloria_rt_catalogue_libnova_LibNovaJNI_getRTS
  (JNIEnv *env, jobject obj1, jobject inObserver, jobject inZoneDate, jstring inObjName, jobject inObjRTS, jobject inReturnedInfo){
  
	//INPUTS
	jclass observer = env->GetObjectClass(inObserver);
	jclass zoneDate = env->GetObjectClass(inZoneDate);
	jclass objName = env->GetObjectClass(inObjName);
	jclass objRTS = env->GetObjectClass(inObjRTS);
	jclass returnedInfo = env->GetObjectClass(inReturnedInfo);
	
	//COMMON
	jfieldID fid;
	
	fid = env->GetFieldID(observer, "longitude", "D");
	jdouble observerLongitude = env->GetDoubleField(inObserver, fid);
	fid = env->GetFieldID(observer, "latitude", "D");
	jdouble observerLatitude = env->GetDoubleField(inObserver, fid);
	fid = env->GetFieldID(observer, "altitude", "D");
	jdouble observerAltitude = env->GetDoubleField(inObserver, fid);
	
	
	//Observer
	struct ln_lnlat_posn observerNova;
	observerNova.lat = observerLatitude;
	observerNova.lng = observerLongitude;
	
	//Error
	fid = env->GetFieldID(returnedInfo, "error", "I");
    env->SetIntField(inReturnedInfo, fid, 0);
	
	//Name
    char * inputName = env->GetStringUTFChars(inObjName, NULL);
    //FREE Section: env->ReleaseStringUTFChars(inObjName, inputName);
	
	//CUSTOM DATE
	struct ln_zonedate dateCustomLocal; //Get julian day for 04/10/1957 19:00:00
    
	fid = env->GetFieldID(zoneDate, "year", "I");
    dateCustomLocal.years = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "month", "I");
    dateCustomLocal.months = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "day", "I");
    dateCustomLocal.days = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "hours", "I");
    dateCustomLocal.hours = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "minutes", "I");
    dateCustomLocal.minutes = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "seconds", "I");
    dateCustomLocal.seconds = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "gmtOff", "I");
    //dateCustomLocal.gmtoff = 7200; //2 horas (UT+2)
    //dateCustomLocal.gmtoff = 3600; //1 hora en invierno (UT+1);
	dateCustomLocal.gmtoff = env->GetIntField(inZoneDate, fid);

    struct ln_date dateCustomUT;
    ln_zonedate_to_date(&dateCustomLocal, &dateCustomUT);

    //Dia juliano usado para posicionamiento
    double dateCustomJD = ln_get_julian_day (&dateCustomUT);
	
	
	/* rise, set and transit *///0 for success, 1 for circumpolar (all day above the horizon), -1 for circumpolar (all day bellow the horizon)
    int circumpolarValue;
	struct ln_zonedate rise, set, transit;
	struct ln_rst_time rst;
	double JD = dateCustomJD;
	int error = 0;
	
	
	if (strcmp(inputName, "moon") == 0){

		circumpolarValue = ln_get_lunar_rst (JD, &observerNova, &rst);
		ln_get_local_date (rst.rise, &rise);
		ln_get_local_date (rst.transit, &transit);
		ln_get_local_date (rst.set, &set);
		//ln_get_lunar_equ_coords (JD, &equ);
		//showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

	} else if (strcmp(inputName, "jupiter") == 0){

                circumpolarValue = ln_get_jupiter_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_jupiter_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "mars") == 0){

                circumpolarValue = ln_get_mars_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_mars_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "mercury") == 0){

                circumpolarValue = ln_get_mercury_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_mercury_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "neptune") == 0){

                circumpolarValue = ln_get_neptune_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_neptune_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "pluto") == 0){

                circumpolarValue = ln_get_pluto_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_pluto_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "saturn") == 0){

                circumpolarValue = ln_get_saturn_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_saturn_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "uranus") == 0){

                circumpolarValue = ln_get_uranus_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_uranus_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "venus") == 0){

                circumpolarValue = ln_get_venus_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_venus_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "sun") == 0){

                circumpolarValue = ln_get_solar_rst (JD, &observerNova, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                //ln_get_solar_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else {
			//UNFOUND
			error = 1;
			fid = env->GetFieldID(returnedInfo, "error", "I");
			env->SetIntField(inReturnedInfo, fid, error);
		}
		
	if (error == 0){ //FOUND
	
		fid = env->GetFieldID(objRTS, "rise_year", "I");
		env->SetIntField(inObjRTS, fid, rise.years);
		fid = env->GetFieldID(objRTS, "rise_month", "I");
		env->SetIntField(inObjRTS, fid, rise.months);
		fid = env->GetFieldID(objRTS, "rise_day", "I");
		env->SetIntField(inObjRTS, fid, rise.days);
		
		fid = env->GetFieldID(objRTS, "rise_hours", "I");
		env->SetIntField(inObjRTS, fid, rise.hours);
		fid = env->GetFieldID(objRTS, "rise_minutes", "I");
		env->SetIntField(inObjRTS, fid, rise.minutes);
		fid = env->GetFieldID(objRTS, "rise_seconds", "I");
		env->SetIntField(inObjRTS, fid, rise.seconds);
		
		fid = env->GetFieldID(objRTS, "transit_year", "I");
		env->SetIntField(inObjRTS, fid, transit.years);
		fid = env->GetFieldID(objRTS, "transit_month", "I");
		env->SetIntField(inObjRTS, fid, transit.months);
		fid = env->GetFieldID(objRTS, "transit_day", "I");
		env->SetIntField(inObjRTS, fid, transit.days);
		
		fid = env->GetFieldID(objRTS, "transit_hours", "I");
		env->SetIntField(inObjRTS, fid, transit.hours);
		fid = env->GetFieldID(objRTS, "transit_minutes", "I");
		env->SetIntField(inObjRTS, fid, transit.minutes);
		fid = env->GetFieldID(objRTS, "transit_seconds", "I");
		env->SetIntField(inObjRTS, fid, transit.seconds);
		
		fid = env->GetFieldID(objRTS, "set_year", "I");
		env->SetIntField(inObjRTS, fid, set.years);
		fid = env->GetFieldID(objRTS, "set_month", "I");
		env->SetIntField(inObjRTS, fid, set.months);
		fid = env->GetFieldID(objRTS, "set_day", "I");
		env->SetIntField(inObjRTS, fid, set.days);
		
		fid = env->GetFieldID(objRTS, "set_hours", "I");
		env->SetIntField(inObjRTS, fid, set.hours);
		fid = env->GetFieldID(objRTS, "set_minutes", "I");
		env->SetIntField(inObjRTS, fid, set.minutes);
		fid = env->GetFieldID(objRTS, "set_seconds", "I");
		env->SetIntField(inObjRTS, fid, set.seconds);
		
		fid = env->GetFieldID(objRTS, "circumpolar", "I");
		env->SetIntField(inObjRTS, fid, circumpolarValue);
	
	}
	
	
	/*****************/
	/* Free resources*/
	/*****************/
	
	env->ReleaseStringUTFChars(inObjName, inputName);
	
	
  
  
 }




/*
 * Class:     eu_gloria_rt_catalogue_libnova_LibNovaJNI
 * Method:    getObjectInfo
 * Signature: (Leu/gloria/rt/catalogue/libnova/LibNovaJNIInput;Leu/gloria/rt/catalogue/libnova/LibNovaJNIObjInfo;)V
 */
JNIEXPORT void JNICALL Java_eu_gloria_rt_catalogue_libnova_LibNovaJNI_getObjectInfo
  (JNIEnv *env, jobject obj1, jobject obj2, jobject obj3){


	//Common	
	jclass inputClass = env->GetObjectClass(obj2);
	jclass outputClass = env->GetObjectClass(obj3);
	jfieldID fid;
	
	//Observer
	struct ln_lnlat_posn observer;

	//Observer - init
	fid = env->GetFieldID(inputClass, "latitude", "D");
	jdouble inputLatitude = env->GetDoubleField(obj2, fid);
	fid = env->GetFieldID(inputClass, "longitude", "D");
        jdouble inputLongitude = env->GetDoubleField(obj2, fid);

	observer.lat = inputLatitude;
	observer.lng = inputLongitude;

	//GMT OFF
	fid = env->GetFieldID(inputClass, "gmtOff", "I");
        jint inputGmtOff = env->GetIntField(obj2, fid);

	//Date
	fid = env->GetFieldID(inputClass, "date", "Ljava/lang/String;");
        jstring inputDateObj  =  (jstring) env->GetObjectField(obj2, fid);

	char * inputDate = env->GetStringUTFChars(inputDateObj, NULL);
	//FREE Section: env->ReleaseStringUTFChars(inputDateObj, inputDate);

	//Epoch
	fid = env->GetFieldID(inputClass, "epoch", "I");
	jint inputEpoch = env->GetIntField(obj2, fid);

	//Name
	fid = env->GetFieldID(inputClass, "name", "Ljava/lang/String;");
        jstring inputNameObj  =  (jstring) env->GetObjectField(obj2, fid);

    char * inputName = env->GetStringUTFChars(inputNameObj, NULL);
    //FREE Section: env->ReleaseStringUTFChars(inputNameObj, inputName);


	printf("INPUT-latitude : %f\n", inputLatitude);
	printf("INPUT-longitude: %f\n", inputLongitude);
	printf("INPUT-epoch    : %d\n", inputEpoch);
	printf("INPUT-date     : %s\n", inputDate);
	printf("INPUT-name     : %s\n", inputName);
	printf("INPUT-gmtOff   : %d\n", inputGmtOff);

	/*********************/
    /* DATE - CUSTOM     */
    /*********************/

    struct ln_zonedate dateCustomLocal;
    /* Get julian day for 04/10/1957 19:00:00 */
    dateCustomLocal.years = getIntFromStr(inputDate, 0, 4);
    dateCustomLocal.months = getIntFromStr(inputDate, 4, 2);
    dateCustomLocal.days = getIntFromStr(inputDate, 6, 2); 
    dateCustomLocal.hours = getIntFromStr(inputDate, 8, 2);
    dateCustomLocal.minutes = getIntFromStr(inputDate, 10, 2);
    dateCustomLocal.seconds = getIntFromStr(inputDate, 12, 2);
    //dateCustomLocal.gmtoff = 7200; //2 horas (UT+2)
    //dateCustomLocal.gmtoff = 3600; //1 hora en invierno (UT+1);
	dateCustomLocal.gmtoff = inputGmtOff;


	//print_date ("INPUT DATE NUMBER", &dateCustomLocal);

    struct ln_date dateCustomUT;
    ln_zonedate_to_date(&dateCustomLocal, &dateCustomUT);

    //Dia juliano usado para posicionamiento
    double dateCustomJD = ln_get_julian_day (&dateCustomUT);

	/***********************/
	/* Look for the object */
	/***********************/	
	struct ln_rst_time rst;
	double JD = dateCustomJD;
	/* rise, set and transit *///0 for success, 1 for circumpolar (all day above the horizon), -1 for circumpolar (all day bellow the horizon)
        int circumpolarValue;
	struct ln_zonedate rise, set, transit;
	struct ln_equ_posn equ;

	//FOUND-->by default
        fid = env->GetFieldID(outputClass, "found", "I");
        env->SetIntField(obj3, fid, true );

	if (strcmp(inputName, "moon") == 0){

		circumpolarValue = ln_get_lunar_rst (JD, &observer, &rst);
		ln_get_local_date (rst.rise, &rise);
		ln_get_local_date (rst.transit, &transit);
		ln_get_local_date (rst.set, &set);
		ln_get_lunar_equ_coords (JD, &equ);
		showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

	} else if (strcmp(inputName, "jupiter") == 0){

                circumpolarValue = ln_get_jupiter_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_jupiter_equ_coords (JD, &equ);
                showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "mars") == 0){

                circumpolarValue = ln_get_mars_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_mars_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "mercury") == 0){

                circumpolarValue = ln_get_mercury_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_mercury_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "neptune") == 0){

                circumpolarValue = ln_get_neptune_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_neptune_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "pluto") == 0){

                circumpolarValue = ln_get_pluto_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_pluto_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "saturn") == 0){

                circumpolarValue = ln_get_saturn_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_saturn_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "uranus") == 0){

                circumpolarValue = ln_get_uranus_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_uranus_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "venus") == 0){

                circumpolarValue = ln_get_venus_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_venus_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else if (strcmp(inputName, "sun") == 0){

                circumpolarValue = ln_get_solar_rst (JD, &observer, &rst);
                ln_get_local_date (rst.rise, &rise);
                ln_get_local_date (rst.transit, &transit);
                ln_get_local_date (rst.set, &set);
                ln_get_solar_equ_coords (JD, &equ);
                //showInfo (inputName, circumpolarValue, rise, set, transit, &equ);

        } else {
		//UNFOUND
		fid = env->GetFieldID(outputClass, "found", "I");	
		env->SetIntField(obj3, fid, 0 );

	}

	/************/
	/*  Results */
	/************/

	fid = env->GetFieldID(outputClass, "ra", "D");
        env->SetDoubleField(obj3, fid, equ.ra);	
	fid = env->GetFieldID(outputClass, "dec", "D");
        env->SetDoubleField(obj3, fid, equ.dec);
		
		
	/*****************/
	/* Free resources*/
	/*****************/
	
	env->ReleaseStringUTFChars(inputDateObj, inputDate);
	env->ReleaseStringUTFChars(inputNameObj, inputName);


}


/*
 * Class:     eu_gloria_rt_catalogue_libnova_LibNovaJNI
 * Method:    getAltazByRadec
 * Signature: (Leu/gloria/rt/catalogue/libnova/LibNovaObserver;Leu/gloria/rt/catalogue/libnova/LibNovaZoneDate;Leu/gloria/rt/catalogue/libnova/LibNovaRaDecJ2000;Leu/gloria/rt/catalogue/libnova/LibNovaAltaz;Leu/gloria/rt/catalogue/libnova/LibNovaReturnedInfo;)V
 */
JNIEXPORT void JNICALL Java_eu_gloria_rt_catalogue_libnova_LibNovaJNI_getAltazByRadec
  (JNIEnv *env, jobject, jobject inObserver, jobject inZoneDate, jobject inRadec, jobject inAltaz, jobject inReturnedInfo){
  
	//INPUTS
	jclass observer = env->GetObjectClass(inObserver);
	jclass zoneDate = env->GetObjectClass(inZoneDate);
	jclass radec = env->GetObjectClass(inRadec);
	jclass altaz = env->GetObjectClass(inAltaz);
	jclass returnedInfo = env->GetObjectClass(inReturnedInfo);
	
	//COMMON
	jfieldID fid;
	
	
	//CUSTOM DATE
	struct ln_zonedate dateCustomLocal; //Get julian day for 04/10/1957 19:00:00
    
	fid = env->GetFieldID(zoneDate, "year", "I");
    dateCustomLocal.years = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "month", "I");
    dateCustomLocal.months = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "day", "I");
    dateCustomLocal.days = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "hours", "I");
    dateCustomLocal.hours = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "minutes", "I");
    dateCustomLocal.minutes = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "seconds", "I");
    dateCustomLocal.seconds = env->GetIntField(inZoneDate, fid);
	fid = env->GetFieldID(zoneDate, "gmtOff", "I");
    //dateCustomLocal.gmtoff = 7200; //2 horas (UT+2)
    //dateCustomLocal.gmtoff = 3600; //1 hora en invierno (UT+1);
	dateCustomLocal.gmtoff = env->GetIntField(inZoneDate, fid);

    struct ln_date dateCustomUT;
    ln_zonedate_to_date(&dateCustomLocal, &dateCustomUT);

    //Dia juliano usado para posicionamiento
    double dateCustomJD = ln_get_julian_day (&dateCustomUT);
	
	
	fid = env->GetFieldID(radec, "dec", "D");
	jdouble radecDec = env->GetDoubleField(inRadec, fid);
	fid = env->GetFieldID(radec, "ra", "D");
	jdouble radecRa = env->GetDoubleField(inRadec, fid);
	
	fid = env->GetFieldID(observer, "latitude", "D");
	jdouble observerLatitude = env->GetDoubleField(inObserver, fid);
	fid = env->GetFieldID(observer, "longitude", "D");
	jdouble observerLongitude = env->GetDoubleField(inObserver, fid);
	
	//Observer
	struct ln_lnlat_posn observerNova;
	observerNova.lat = observerLatitude;
	observerNova.lng = observerLongitude;
	 
	struct ln_equ_posn posRadec;
	posRadec.ra = radecRa;
	posRadec.dec = radecDec;
	
	struct ln_hrz_posn posAltaz;
	ln_get_hrz_from_equ(&posRadec, &observerNova, dateCustomJD, &posAltaz);
	
	//ALTAZ
	fid = env->GetFieldID(altaz, "alt", "D");
    env->SetDoubleField(inAltaz, fid, posAltaz.alt);
	
	fid = env->GetFieldID(altaz, "az", "D");
    env->SetDoubleField(inAltaz, fid, posAltaz.az);
	
	//Error
	fid = env->GetFieldID(returnedInfo, "error", "I");
    env->SetIntField(inReturnedInfo, fid, 0);
	
	
	
}

/*
 * Class:     eu_gloria_rt_catalogue_libnova_LibNovaJNI
 * Method:    getAngularDistance
 * Signature: (Leu/gloria/rt/catalogue/libnova/LibNovaRaDecJ2000;Leu/gloria/rt/catalogue/libnova/LibNovaRaDecJ2000;Leu/gloria/rt/catalogue/libnova/LibNovaAngularDistance;Leu/gloria/rt/catalogue/libnova/LibNovaReturnedInfo;)V
 */
JNIEXPORT void JNICALL Java_eu_gloria_rt_catalogue_libnova_LibNovaJNI_getAngularDistance
  (JNIEnv *env, jobject obj1, jobject inRadec1, jobject inRadec2, jobject inAngularDistance, jobject inReturnedInfo){
  
	//printf("POINT-1\n");
  
	//INPUTS
	jclass obj1Radec = env->GetObjectClass(inRadec1);
	jclass obj2Radec = env->GetObjectClass(inRadec2);
	jclass angularDistace = env->GetObjectClass(inAngularDistance);
	jclass returnedInfo = env->GetObjectClass(inReturnedInfo);
	
	//COMMON
	jfieldID fid;
	
	fid = env->GetFieldID(obj1Radec, "dec", "D");
	jdouble radec1Dec = env->GetDoubleField(inRadec1, fid);
	fid = env->GetFieldID(obj1Radec, "ra", "D");
	jdouble radec1Ra = env->GetDoubleField(inRadec1, fid);
	
	fid = env->GetFieldID(obj2Radec, "dec", "D");
	jdouble radec2Dec = env->GetDoubleField(inRadec2, fid);
	fid = env->GetFieldID(obj2Radec, "ra", "D");
	jdouble radec2Ra = env->GetDoubleField(inRadec2, fid);
	
	//printf("POINT-2. OBJ1: dec=%f, ra=%f\n", radec1Dec, radec1Ra);
	//printf("POINT-3. OBJ2: dec=%f, ra=%f\n", radec2Dec, radec2Ra);
	
	//Distance-Calculation
	double distance = ln_rad_to_deg(
							acos(
                                sin(ln_deg_to_rad(radec1Dec))
                                *sin(ln_deg_to_rad(radec2Dec))
                                + cos(ln_deg_to_rad(radec1Dec))
                                  *cos(ln_deg_to_rad(radec2Dec))
                                  *cos(ln_deg_to_rad(radec1Ra)-ln_deg_to_rad(radec2Ra))
							)
						);
						
	//printf("POINT-4. Distance=%f\n", distance);
						
	//Distance-Return 
	fid = env->GetFieldID(angularDistace, "degrees", "D");
    env->SetDoubleField(inAngularDistance, fid, distance);

	//Error
	fid = env->GetFieldID(returnedInfo, "error", "I");
    env->SetIntField(inReturnedInfo, fid, 0);
  
 }

