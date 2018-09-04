
/**
 * IZyhtWebServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

    package nc.itf.crd.webservice.izyhtwebservice;

    /**
     *  IZyhtWebServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class IZyhtWebServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public IZyhtWebServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public IZyhtWebServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for inteAccrData method
            * override this method for handling normal response from inteAccrData operation
            */
           public void receiveResultinteAccrData(
                    nc.itf.crd.webservice.izyhtwebservice.InteAccrDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inteAccrData operation
           */
            public void receiveErrorinteAccrData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for realFeeData method
            * override this method for handling normal response from realFeeData operation
            */
           public void receiveResultrealFeeData(
                    nc.itf.crd.webservice.izyhtwebservice.RealFeeDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from realFeeData operation
           */
            public void receiveErrorrealFeeData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for accGrantData method
            * override this method for handling normal response from accGrantData operation
            */
           public void receiveResultaccGrantData(
                    nc.itf.crd.webservice.izyhtwebservice.AccGrantDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from accGrantData operation
           */
            public void receiveErroraccGrantData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bdCubDocData method
            * override this method for handling normal response from bdCubDocData operation
            */
           public void receiveResultbdCubDocData(
                    nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bdCubDocData operation
           */
            public void receiveErrorbdCubDocData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for feePlanData method
            * override this method for handling normal response from feePlanData operation
            */
           public void receiveResultfeePlanData(
                    nc.itf.crd.webservice.izyhtwebservice.FeePlanDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from feePlanData operation
           */
            public void receiveErrorfeePlanData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for intePlanData method
            * override this method for handling normal response from intePlanData operation
            */
           public void receiveResultintePlanData(
                    nc.itf.crd.webservice.izyhtwebservice.IntePlanDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from intePlanData operation
           */
            public void receiveErrorintePlanData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for realInteData method
            * override this method for handling normal response from realInteData operation
            */
           public void receiveResultrealInteData(
                    nc.itf.crd.webservice.izyhtwebservice.RealInteDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from realInteData operation
           */
            public void receiveErrorrealInteData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unObGageBVOData method
            * override this method for handling normal response from unObGageBVOData operation
            */
           public void receiveResultunObGageBVOData(
                    nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unObGageBVOData operation
           */
            public void receiveErrorunObGageBVOData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for overDueData method
            * override this method for handling normal response from overDueData operation
            */
           public void receiveResultoverDueData(
                    nc.itf.crd.webservice.izyhtwebservice.OverDueDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from overDueData operation
           */
            public void receiveErroroverDueData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancelData method
            * override this method for handling normal response from cancelData operation
            */
           public void receiveResultcancelData(
                    nc.itf.crd.webservice.izyhtwebservice.CancelDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancelData operation
           */
            public void receiveErrorcancelData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for realPiiData method
            * override this method for handling normal response from realPiiData operation
            */
           public void receiveResultrealPiiData(
                    nc.itf.crd.webservice.izyhtwebservice.RealPiiDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from realPiiData operation
           */
            public void receiveErrorrealPiiData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for realPriData method
            * override this method for handling normal response from realPriData operation
            */
           public void receiveResultrealPriData(
                    nc.itf.crd.webservice.izyhtwebservice.RealPriDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from realPriData operation
           */
            public void receiveErrorrealPriData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for alObGageData method
            * override this method for handling normal response from alObGageData operation
            */
           public void receiveResultalObGageData(
                    nc.itf.crd.webservice.izyhtwebservice.AlObGageDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from alObGageData operation
           */
            public void receiveErroralObGageData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bankDzdData method
            * override this method for handling normal response from bankDzdData operation
            */
           public void receiveResultbankDzdData(
                    nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bankDzdData operation
           */
            public void receiveErrorbankDzdData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for feeAccrData method
            * override this method for handling normal response from feeAccrData operation
            */
           public void receiveResultfeeAccrData(
                    nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from feeAccrData operation
           */
            public void receiveErrorfeeAccrData(java.lang.Exception e) {
            }
                


    }
    