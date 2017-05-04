
This project is a desktop application to predict project delivery time by taking past data information from Atlassian Jira.

Basically it gives a project finish time projection by doing statistical data processing on previous projects data and helps users to forecast their project budget and prepare sales proposals.
 

###how to build and run?

This project uses sqlite database, you must configure the database first.
For Database settings
Create a folder named sqlite in C:\
Move the jiraProject.db file into the sqlite folder
When run the project, you must configure the settings on config.properties file.

###For Jira Settings
Change the relevant sections in the config.properties file

BASE_URL="<<< JIRA_URL >>>" (For example: https://jira.atlassian.com/)

AUTH_URL=rest/auth/1/session (This URL is default URL for authentication in JIRA API)

JQL_URL=rest/api/latest/search?jql\= (This URL is default for JQL filter in JIRA API)

MAX_RESULT=How many issues get by JIRA API to resampling for estimating delivery time of project?

LAST_PULL=The number of days, how many days will be kept the project data in SQLite database.( min 1 day )





