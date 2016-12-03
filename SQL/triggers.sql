DROP TRIGGER IF EXISTS DeleteVehicleTrigger;
delimiter //
CREATE TRIGGER DeleteVehicleTrigger
AFTER DELETE ON vehicle FOR EACH ROW
BEGIN
delete from reservation where v_id =Old.v_id;
END;//
delimiter ;


DROP TRIGGER IF EXISTS DeleteUserTrigger;
delimiter //
CREATE TRIGGER DeleteUserTrigger
AFTER DELETE ON customer FOR EACH ROW
BEGIN
delete from reservation where c_id =Old.c_id;
END;//
delimiter ;