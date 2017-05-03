BEGIN TRANSACTION;
CREATE TABLE status (name text not null);
CREATE TABLE "projects" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`project_name`	text NOT NULL,
	`created_date`	integer
);
CREATE TABLE "itemsForHistories" (
	`itemId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`fromString`	TEXT,
	`toString`	TEXT,
	`historyId`	INTEGER,
	FOREIGN KEY(`historyId`) REFERENCES histories('historyId')
);
CREATE TABLE "issues" (
	`project_id`	INTEGER NOT NULL,
	`issue_key`	text NOT NULL,
	`created_date`	text NOT NULL,
	`completed_date`	text NOT NULL,
	`time_cycle`	int NOT NULL,
	`issue_type`	text NOT NULL,
	`issueId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	FOREIGN KEY(`project_id`) REFERENCES `projets`(`id`)
);
CREATE TABLE issue_types (name text not null, icon_url text);
CREATE TABLE "issue_type_list" (
	`project_id`	INTEGER,
	`issue`	TEXT,
	FOREIGN KEY(`project_id`) REFERENCES `projects`(`project_id`)
);
CREATE TABLE "histories" (
	`historyId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`issueId`	INTEGER,
	`hDate`	INTEGER
);
COMMIT;
