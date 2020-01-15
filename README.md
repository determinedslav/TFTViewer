# TFTViewer


A Java web application that uses data sent from the Riot Games API to visualise in a web page and store into a local database.


Allows for the creation of seperate user accounts each storing statistics only requested by their respective user;

Can display information for Ranked eamFight Tactics and Ranked Summoner's Rift Solo/Duo.


Created using Spring with H2 dataabase


Notes:

The API key provided by Riot Games lasts only for 24 hours, a new Riot Games API key is needed for the application to work properly;

Access-Control-Allow-Origin is missing from Riot Games API response header and needs to be added seperately (Example: Using a browser plugin).
