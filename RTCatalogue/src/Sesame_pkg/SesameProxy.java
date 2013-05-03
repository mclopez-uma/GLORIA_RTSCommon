package Sesame_pkg;

public class SesameProxy implements Sesame_pkg.Sesame {
  private String _endpoint = null;
  private Sesame_pkg.Sesame sesame = null;
  
  public SesameProxy() {
    _initSesameProxy();
  }
  
  public SesameProxy(String endpoint) {
    _endpoint = endpoint;
    _initSesameProxy();
  }
  
  private void _initSesameProxy() {
    try {
      sesame = (new Sesame_pkg.SesameServiceLocator()).getSesame();
      if (sesame != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sesame)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sesame)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sesame != null)
      ((javax.xml.rpc.Stub)sesame)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Sesame_pkg.Sesame getSesame() {
    if (sesame == null)
      _initSesameProxy();
    return sesame;
  }
  
  public java.lang.String sesame(java.lang.String name, java.lang.String resultType) throws java.rmi.RemoteException{
    if (sesame == null)
      _initSesameProxy();
    return sesame.sesame(name, resultType);
  }
  
  public java.lang.String sesame(java.lang.String name, java.lang.String resultType, boolean all) throws java.rmi.RemoteException{
    if (sesame == null)
      _initSesameProxy();
    return sesame.sesame(name, resultType, all);
  }
  
  public java.lang.String sesame(java.lang.String name, java.lang.String resultType, boolean all, java.lang.String service) throws java.rmi.RemoteException{
    if (sesame == null)
      _initSesameProxy();
    return sesame.sesame(name, resultType, all, service);
  }
  
  public java.lang.String sesameXML(java.lang.String name) throws java.rmi.RemoteException{
    if (sesame == null)
      _initSesameProxy();
    return sesame.sesameXML(name);
  }
  
  public java.lang.String sesame(java.lang.String name) throws java.rmi.RemoteException{
    if (sesame == null)
      _initSesameProxy();
    return sesame.sesame(name);
  }
  
  public java.lang.String getAvailability() throws java.rmi.RemoteException{
    if (sesame == null)
      _initSesameProxy();
    return sesame.getAvailability();
  }
  
  public void main(java.lang.String[] in0) throws java.rmi.RemoteException{
    if (sesame == null)
      _initSesameProxy();
    sesame.main(in0);
  }
  
  
}