
This project is a desktop application to use The NoEstimates Movement on your project which developed on Atlassian JIRA API.

Basicly it gives a project finish time projection by analyzing previous project data and helps users to forecast their project budget and prepare sales proposals.
 

###how to build and run?

This project uses sqlite database, you must configure the database first.
For Database settings
Create a folder named sqlite in C:\
Move the jiraProject.db file into the sqlite folder
When run the project, you mush configure the settings on Login Panel. You can open the settings menu by the button which is on the top-right of Login Panel. For

###For Jira Settings
Change the relevant sections in the config.properties file

BASE_URL="<<< JIRA_URL >>>" (For example: https://jira.atlassian.com/)

AUTH_URL=rest/auth/1/session (This URL is default URL for authentication in JIRA API)

JQL_URL=rest/api/latest/search?jql\= (This URL is default for JQL filter in JIRA API)

MAX_RESULT=How many issues get by JIRA API to resampling for estimating delivery time of project?

LAST_PULL=The number of days, how many days will be kept the project datas in SQLite database.( min 1 day )





