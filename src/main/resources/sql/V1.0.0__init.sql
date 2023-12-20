CREATE TABLE `workday`  (
  `date` date NOT NULL COMMENT '日期',
  `workday` int NOT NULL COMMENT '是否工作日',
  PRIMARY KEY (`date`)
);
ALTER TABLE `workday` 
ADD COLUMN `reason` varchar(255) NULL COMMENT '节假日' AFTER `workday`;