*********************
JSAGA-Adaptor-rOCCI v1.1.0 Docs
*********************

============
About
============

.. image:: images/logo-jsaga.png
   :align: left
   :target: http://software.in2p3.fr/jsaga/latest-release/

.. image:: images/OCCI-logo.png
   :align: left
   :target: http://occi-wg.org/
-------------

.. _1: https://www.ogf.org
.. _2: http://software.in2p3.fr/jsaga/latest-release/
.. _3: http://occi-wg.org/
.. _CHAIN_REDS: https://www.chain-project.eu/

The Simple API for Grid Applications (SAGA) is a family of related standards specified by the Open Grid Forum [1_] to define an application programming interface (API) for common distributed computing functionality.

These APIs does not strive to replace Globus or similar grid computing middleware systems, and does not target middleware developers, but application developers with no background on grid computing. Such developers typically wish to devote their time to their own goals and minimize the time spent coding infrastructure functionality. The API insulates application developers from middleware.

The specification of services, and the protocols to interact with them, is out of the scope of SAGA. Rather, the API seeks to hide the detail of any service infrastructures that may or may not be used to implement the functionality that the application developer needs. The API aligns, however, with all middleware standards within Open Grid Forum (OGF).

JSAGA [2_] is a Java implementation of the Simple API for Grid Applications (SAGA) specification from the Open Grid Forum (OGF). It permits seamless data and execution management between heterogeneous grid infrastructures.

The current stable release is available at:
- http://software.in2p3.fr/jsaga/

The current development SNAPSHOT is available at:
- http://software.in2p3.fr/jsaga/dev/

In the contest of the CHAIN_REDS_ project a new JSAGA adaptor for OCCI-complaint [3_] cloud middleware has been developed to demonstrate the interoperatibility between different open-source cloud providers.

Using the rOCCI implementation of the OCCI standard, the adaptor takes care of: 

(i) switching-on the VM pre-installed with the required application, 
(ii) establishing a secure connection to it signed using a digital “robot” certificate, 
(iii) staging the input file(s) in the VM, 
(iv) executing the application, 
(v) retrieving the output file(s) at the end of the computation and
(vi) killing the VM.

============
Installation
============

============
Usage
============

============
Support
============
Please feel free to contact us any time if you have any questions or comments.

.. _INFN: http://www.ct.infn.it/

:Authors:

 `Roberto BARBERA <mailto:roberto.barbera@ct.infn.it>`_ - Italian National Institute of Nuclear Physics (INFN_),
 
 `Giuseppe LA ROCCA <mailto:giuseppe.larocca@ct.infn.it>`_ - Italian National Institute of Nuclear Physics (INFN_),
 
 `Diego SCARDACI <mailto:diego.scardaci@ct.infn.it>`_ - Italian National Institute of Nuclear Physics (INFN_)
 
:Version: v1.1.0, 2015

:Date: June 3rd, 2015 10:53
