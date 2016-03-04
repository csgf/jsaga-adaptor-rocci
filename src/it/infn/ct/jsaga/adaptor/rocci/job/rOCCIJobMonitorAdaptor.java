/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package it.infn.ct.jsaga.adaptor.rocci.job;

import fr.in2p3.jsaga.adaptor.base.usage.U;
import fr.in2p3.jsaga.adaptor.base.usage.UAnd;
import fr.in2p3.jsaga.adaptor.base.usage.UOptional;
import fr.in2p3.jsaga.adaptor.base.usage.Usage;
import it.infn.ct.jsaga.adaptor.rocci.rOCCIAdaptorCommon;

import fr.in2p3.jsaga.adaptor.job.control.manage.ListableJobAdaptor;
import fr.in2p3.jsaga.adaptor.job.monitor.JobInfoAdaptor;
import fr.in2p3.jsaga.adaptor.job.monitor.JobStatus;
import fr.in2p3.jsaga.adaptor.job.monitor.QueryIndividualJob;
import fr.in2p3.jsaga.adaptor.ssh3.job.SSHJobMonitorAdaptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import org.apache.log4j.Logger;

import org.ogf.saga.error.*;

/* *********************************************
 * *** Istituto Nazionale di Fisica Nucleare ***
 * ***      Sezione di Catania (Italy)       ***
 * ***        http://www.ct.infn.it/         ***
 * *********************************************
 * File:    rOCCIJobControlAdaptor.java
 * Authors: Giuseppe LA ROCCA, Diego SCARDACI
 * Email:   <giuseppe.larocca, 
 *           diego.scardaci,
 *           riccardo.bruno>@ct.infn.it
 * Ver.:    1.0.4
 * Date:    24 September 2014
 * *********************************************/

public class rOCCIJobMonitorAdaptor extends rOCCIAdaptorCommon 
                                      implements QueryIndividualJob, 
                                                 ListableJobAdaptor, 
                                                 JobInfoAdaptor
{      
  
  protected static final String ATTRIBUTES_TITLE = "attributes_title";
  protected static final String MIXIN_OS_TPL = "mixin_os_tpl";
  protected static final String MIXIN_RESOURCE_TPL = "mixin_resource_tpl";
  protected static final String PREFIX = "prefix";  
  
  private SSHJobMonitorAdaptor sshMonitorAdaptor = 
          new SSHJobMonitorAdaptor();
  
  private static final Logger log = 
          Logger.getLogger(rOCCIJobMonitorAdaptor.class);
  
  @Override
  public void connect(String userInfo, String host, int port, 
                      String basePath, Map attributes) 
              throws NotImplementedException, 
                     AuthenticationFailedException, 
                     AuthorizationFailedException, 
                     IncorrectURLException, 
                     BadParameterException, 
                     TimeoutException, 
                     NoSuccessException 
  {      
    super.connect(userInfo, host, port, basePath, attributes);
    sshMonitorAdaptor.setSecurityCredential(credential.getSSHCredential());  
    log.info("rOCCI_Host: '"+host+"'");
    log.info("rOCCI_Port: '"+port+"'");
    // Following are set in rOCCIAdaptorCommon
    log.info("rOCCI_resource: '"+rOCCI_resource+"'");
    log.info("rOCCI_sshHost : '"+rOCCI_sshHost+"'");
    log.info("rOCCI_sshPort : '"+rOCCI_sshPort+"'");
  }
    
  @Override
  public String getType() {
    return "rocci";
  }
  
  /* No more necessary
  public void setOCCIResource(String occiRes) {
      log.info("Setting up rOCCI_Resource: '"+occiRes+"'");
      rOCCI_resource = occiRes;      
  }
  public void setSSHHost(String host) {
      log.info("Setting up rOCCI_sshHost: '"+host+"'");
      rOCCI_sshHost = host;      
  }
  public void setSSHPort(int port) {
      log.info("Setting up rOCCI_sshPort: '"+port+"'");
      rOCCI_sshPort = port;      
  }
  */ 
  
  @Override
  public JobStatus getStatus(String nativeJobId) 
                   throws TimeoutException, NoSuccessException 
  {        
    JobStatus result = null;
    String _publicIP = nativeJobId.substring(nativeJobId.indexOf("@")+1, 
                                             nativeJobId.indexOf("#"));
    int _sshPort = Integer.parseInt(nativeJobId.
            substring(nativeJobId.indexOf("#")+1, 
                      nativeJobId.indexOf("$")));
    
    log.info("Getting status for job: '"+nativeJobId+"'");
    log.info("             _publicIP: '"+_publicIP+"'");
    log.info("              _sshPort: '"+_sshPort+"'");    
    
    String _nativeJobId = nativeJobId.substring(0, nativeJobId.indexOf("@"));
    
    log.info("       _nativeJobId(@): '"+_nativeJobId+"' ("+rOCCI_resource+")");
    log.info("-------------------------'");
    log.info("        rOCCI_resource: '"+_publicIP+"' ("+rOCCI_resource+")");
    log.info("        rOCCI_sshHost : '"+_publicIP+"' ("+rOCCI_sshHost+")");
    log.info("        rOCCI_sshPort : '"+_sshPort+"' ("+rOCCI_sshPort+")");
    try {
        sshMonitorAdaptor.connect(null, _publicIP, _sshPort, null, new HashMap());
        result = sshMonitorAdaptor.getStatus(_nativeJobId);
    } catch (NotImplementedException ex) {
        log.info("GetStatus failed");
        log.info(ex.toString());
        java.util.logging.Logger.getLogger(rOCCIJobMonitorAdaptor.class.getName()).log(Level.SEVERE, null, ex);
    } catch (AuthenticationFailedException ex) {
        log.info("GetStatus failed");
        log.info(ex.toString());
        java.util.logging.Logger.getLogger(rOCCIJobMonitorAdaptor.class.getName()).log(Level.SEVERE, null, ex);
    } catch (AuthorizationFailedException ex) {
        log.info("GetStatus failed");
        log.info(ex.toString());
        java.util.logging.Logger.getLogger(rOCCIJobMonitorAdaptor.class.getName()).log(Level.SEVERE, null, ex);
    } catch (BadParameterException ex) {
        log.info("GetStatus failed");
        log.info(ex.toString());
        java.util.logging.Logger.getLogger(rOCCIJobMonitorAdaptor.class.getName()).log(Level.SEVERE, null, ex);
    }
    log.info("                Status: '"+result+"'");
  
    return result;    
  }
  
  @Override
  public String[] list() throws PermissionDeniedException, TimeoutException, NoSuccessException 
  {
    log.info("Calling the list() method");
    
    return sshMonitorAdaptor.list();
  }       
  
  @Override
  public Date getCreated(String nativeJobId) 
              throws NotImplementedException, NoSuccessException 
  {    
    Date result = null;
    String _nativeJobId = nativeJobId.substring(0, nativeJobId.indexOf("@"));
    
    result = sshMonitorAdaptor.getCreated(_nativeJobId);
    log.info("Calling the getCreated() method");
    
    return result;
  }
  
  @Override
  public Date getStarted(String nativeJobId) 
              throws NotImplementedException, NoSuccessException 
  {     
    Date result = null;
    String _nativeJobId = nativeJobId.substring(0, nativeJobId.indexOf("@"));
    
    result = sshMonitorAdaptor.getStarted(_nativeJobId);
    log.info("Calling the getStarted() method");
    
    return result;
  }
  
  @Override
  public Date getFinished(String nativeJobId) 
              throws NotImplementedException, NoSuccessException 
  {    
    Date result = null;
    String _nativeJobId = nativeJobId.substring(0, nativeJobId.indexOf("@"));
    
    result = sshMonitorAdaptor.getFinished(_nativeJobId);
    log.info("Calling the getFinished() method");
    
    return result;
  }

  @Override
  public Integer getExitCode(String nativeJobId) 
                 throws NotImplementedException, NoSuccessException 
  {        
    Integer result = null;
    String _nativeJobId = nativeJobId.substring(0, nativeJobId.indexOf("@"));
    
    result = sshMonitorAdaptor.getExitCode(_nativeJobId);
    log.info("Calling the getExitCode() method");
    
    return result;
  }
  
  @Override
  public String[] getExecutionHosts(String nativeJobId) 
                  throws NotImplementedException, NoSuccessException 
  {        
    String[] result = null;
    String _nativeJobId = nativeJobId.substring(0, nativeJobId.indexOf("@"));
    
    result = sshMonitorAdaptor.getExecutionHosts(_nativeJobId);
    log.info("Calling the getExecutionHosts() method");
    
    return result;
  }
  
  @Override
  public Usage getUsage()
  {
    return new UAnd.Builder()
                    .and(super.getUsage())
                    .and(new U(ATTRIBUTES_TITLE))
                    .and(new U(MIXIN_OS_TPL))
                    .and(new U(MIXIN_RESOURCE_TPL))
                    .and(new UOptional(PREFIX))
                    .build();
  }
}
