# Plant Irrigation App

The purpose of this Android application is to provide a common way for monitoring and managment of an embedded system dedicated to plants irrigation. Communication is performed through massage passing via MQTT broker and subscription to specific topics. In the current implementation every user can obtain information on when the irrigation of specific plant happend (data and time), what was the soil's moisture level and additionaly some hints about plant's growing. Bellow you can find an abstract illustration of the whole system and how each components interact.

## System components and communication (overview)

![System Overviw Pic](https://i.ibb.co/WnYxNSV/Irrigation-Syst-Overview.png)

<h3 align="left">Tech Stack:</h3>
<p align="left"> <a href="https://developer.android.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original-wordmark.svg" alt="android" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://firebase.google.com/" target="_blank"> <img src="https://www.vectorlogo.zone/logos/firebase/firebase-icon.svg" alt="firebase" width="40" height="40"/> </a> 
<a href="https://firebase.google.com/" target="_blank"> <img src="https://www.vectorlogo.zone/logos/nodejs/nodejs-ar21.svg" alt="NodeJs" width="60" height="50"/> </a> </p>