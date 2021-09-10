# Plant Irrigation App

The purpose of this Android application is to provide a common way for monitoring and managment of an embedded system dedicated to plants irrigation. Communication is performed through massage passing via MQTT broker and subscription to specific topics. In the current implementation every user can obtain information on when the irrigation of specific plant happend (data and time), what was the soil's moisture level and additionaly some hints about plant's growing. Bellow you can find an abstract illustration of the whole system and how each components interact.

## System components and communication (overview)

![System Overviw Pic](https://i.ibb.co/WnYxNSV/Irrigation-Syst-Overview.png)