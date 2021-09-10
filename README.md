# Plant Irrigation App

The purpose of this Android application is to provide a common way for monitoring and managment of an embedded system dedicated to plants irrigation. Communication is encrypted using **SSL protocol** and is performed through massage passing via **MQTT broker** and subscription to specific topics.. In the current implementation every user can obtain information on when the irrigation of specific plant happend (data and time), what was the soil's moisture level and additionaly some hints about plant's growing. Bellow you can find an abstract illustration of the whole system and how each components interact.

#### Management options:
  * **_Pump management (on/off)_**
  * **_Moisture Managment (maintainging certain soil moisture in %)_**
  * **_Delayed Pump Start (start pump at specific date and time with desired durtion)_**
  
## System components and communication (overview)

![System Overviw Pic](https://i.ibb.co/WnYxNSV/Irrigation-Syst-Overview.png)

## Screenshots of Activties
#### Home Activity (list of brokers)
![HomeActivityPic](https://i.ibb.co/KVy9jSP/Hnet-com-image.jpg)
<br><br>
### Plants related to specific broker
![PlantsListPic](https://i.ibb.co/mXXbTp8/Plants-List-Scr-Shot.jpg)
<br><br>
### Subscription topics related to MQTT broker
![TopicsListPic](https://i.ibb.co/98cvBMz/Topics-List-Scr-Shot.jpg)
<br><br>
### Interface for managing irrigation regimes
![IrrigationManagementBoard](https://i.ibb.co/b1ryLWg/Management-Board-Prt-Scr.jpg)
<br><br>
### Screen for monitoring real-time data
![RealtimeDataPic](https://i.ibb.co/1qDFQZ6/Realtime-Data-Scrn-Shot-2.jpg)
<br><br>
### Screen for showing irrigation archive
![IrrigationArchivePci](https://i.ibb.co/qDQshHZ/Irrigation-Archive-Scrn-Shot.jpg)

<br><br>

<h3 align="left">Tech Stack:</h3>
<p align="left"> <a href="https://developer.android.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original-wordmark.svg" alt="android" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://firebase.google.com/" target="_blank"> <img src="https://www.vectorlogo.zone/logos/firebase/firebase-icon.svg" alt="firebase" width="40" height="40"/> </a> 
<a href="https://nodejs.org/en/" target="_blank"> <img src="https://www.vectorlogo.zone/logos/nodejs/nodejs-ar21.svg" alt="NodeJs" width="60" height="50"/> </a>  <a href="https://mosquitto.org/" target="_blank"> <img src="https://mosquitto.org/images/mosquitto-text-side-28.png" alt="MosquittoBroker" width="90" height="40"/> </a> </p>