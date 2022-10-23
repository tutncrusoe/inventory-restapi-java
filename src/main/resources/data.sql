INSERT INTO inventory_app.i_role (id,created_at,created_by,last_modified_at,last_modified_by,version,i_description,i_name) VALUES
	 ('8811e605-eda3-4de7-b7e8-b74f6bb79590','2022-10-25 15:55:00.651548','superadmin@gmail.com','2022-10-25 15:55:00.651548','superadmin@gmail.com',0,'description of ROLE_USER',0)
WHERE NOT EXISTS (SELECT * FROM inventory_app.i_role WHERE id='8811e605-eda3-4de7-b7e8-b74f6bb79590');

INSERT INTO inventory_app.i_role (id,created_at,created_by,last_modified_at,last_modified_by,version,i_description,i_name) VALUES
	 ('ca3317ff-693a-4635-8b2f-4123491b6e8e','2022-10-25 01:26:52.931035','superadmin@gmail.com','2022-10-25 01:26:52.931035','superadmin@gmail.com',0,'description of ROLE_admin',1)
WHERE NOT EXISTS (SELECT * FROM inventory_app.i_role WHERE id='ca3317ff-693a-4635-8b2f-4123491b6e8e');
