USE QLTT;

INSERT INTO STUDENTS (STU_EMAIL,STU_NAME,STU_PHONE,STU_ADDRESS,STU_GENDER,STU_BIRTHDATE,STU_JOINDATE,STU_SCHOOL) 
	VALUES  ("dictum@penatibuset.ca","Liberty Alexander","0875296913","384-5368 Sodales Ave","M","2000/03/28","2020/08/16","Arnesano"),
			("rhoncus.Nullam.velit@velitegestaslacinia.net","Sage Petersen","0335977337","Ap #717-4942 Diam. Road","F","2000/05/07","2020/03/20","Schifferstadt"),
            ("nunc.ullamcorper.eu@Nullam.org","Nissim Kirby","0135570011","657-3910 Quis Av.","F","2000/07/27","2020/10/29","Isca sullo Ionio"),
            ("Pellentesque.habitant.morbi@estmauris.ca","Brynne Emerson","0087828200","Ap #311-6495 Metus Rd.","M","2000/08/01","2020/03/24","Cherain"),
            ("at.pede.Cras@mattisvelitjusto.edu","Preston Stewart","0293505924","P.O. Box 143, 5890 Erat. Street","M","2000/04/24","2020/11/07","Steenokkerzeel"),
            ("accumsan@nuncQuisque.com","Adele Hart","0116869170","6613 Ultricies Street","F","2000/01/18","2020/01/26","Middelkerke"),
            ("Lorem.ipsum@Duisvolutpatnunc.edu","Abra Greene","0049447557","P.O. Box 287, 1044 Nec Road","F","2000/10/06","2020/05/07","Hamilton"),
            ("lorem.ipsum.sodales@mollisnoncursus.edu","Camden Burke","0726104978","265-8984 Sit St.","M","2000/09/13","2020/03/13","San Fratello"),
            ("eu.neque.pellentesque@magnamalesuadavel.com","Jemima Cote","0636175460","Ap #659-1790 Eget Rd.","F","2000/05/03","2020/10/15","Renaico"),
            ("Nam@consectetueradipiscingelit.co.uk","Unity Cummings","0540888745","Ap #214-3111 Natoque Avenue","M","2000/10/18","2020/04/24","Tarzo");
            
INSERT INTO TEACHERS (TEA_EMAIL,TEA_NAME,TEA_PHONE,TEA_ADDRESS,TEA_GENDER,TEA_BIRTHDATE,TEA_HIREDATE,TEA_SCHOOL,TEA_SALARYRATE) 
	VALUES  ("nonummy@at.com","Wanda Witt","0903523739","5037 Lobortis Rd.","F","2000/02/11","2020/07/19","Osgoode","0.78"),
			("augue.eu@amet.co.uk","Ava Ramsey","0505194637","P.O. Box 386, 6058 Turpis St.","M","2000/07/24","2020/06/14","Matlock","0.31"),
            ("dignissim@gravidaAliquamtincidunt.edu","Fuller James","0921267902","Ap #392-5666 Gravida Av.","F","2000/06/08","2020/10/08","Anzegem","0.72"),
            ("odio.Nam@eget.ca","Lee Fox","0110422110","P.O. Box 311, 4036 Nec, Av.","M","2000/03/15","2020/12/21","Aurangabad","0.07"),
            ("at.fringilla.purus@vel.ca","Dexter Powers","0872383615","Ap #800-4830 Nibh Road","F","2000/11/17","2020/08/15","South Portland","0.89");

INSERT INTO ACCOUNTS (ACC_USERNAME,ACC_PASSWORD,ACC_ISACTIVE,ACC_ROLE)
	VALUES  ("dictum@penatibuset.ca","1234","T",1),
			("rhoncus.Nullam.velit@velitegestaslacinia.net","1234","T",1),
            ("nunc.ullamcorper.eu@Nullam.org","1234","F",1),
            ("Pellentesque.habitant.morbi@estmauris.ca","1234","T",1),
            ("at.pede.Cras@mattisvelitjusto.edu","1234","F",1),
            ("accumsan@nuncQuisque.com","1234","F",1),
            ("Lorem.ipsum@Duisvolutpatnunc.edu","1234","T",1),
            ("lorem.ipsum.sodales@mollisnoncursus.edu","1234","T",1),
            ("eu.neque.pellentesque@magnamalesuadavel.com","1234","T",1),
            ("Nam@consectetueradipiscingelit.co.uk","1234","F",1),
			("nonummy@at.com","1234","T",2),
            ("augue.eu@amet.co.uk","1234","T",2),
            ("dignissim@gravidaAliquamtincidunt.edu","1234","F",2),
            ("odio.Nam@eget.ca","1234","F",2),
            ("at.fringilla.purus@vel.cau","1234","T",2);
            
INSERT INTO SUBJECTS (SUB_NAME,SUB_STARTDATE,SUB_SCHEDULE,SUB_ROOM,SUB_TUITION,TEA_ID)
	VALUES  ("Malkajgiri","2022/01/09","T0,0,5 4h86","T196",226066,1),
			("Forres","2022/01/24","T3,1,8 8h91","Y165",253053,1),
            ("Vespolate","2022/01/03","T4,5,1 0h63","W402",241594,1),
            ("Baracaldo","2022/01/02","T2,7,5 9h35","R673",288341,1),
            ("Whyalla","2022/01/13","T6,2,6 7h07","P134",247830,2),
            ("Inverbervie","2022/01/08","T8,7,0 8h68","J982",218330,1),
            ("Macclesfield","2022/01/07","T8,2,0 8h68","S895",220895,2),
            ("Episcopia","2022/01/04","T5,3,1 4h53","W323",211920,2),
            ("Whitehorse","2022/01/06","T4,2,5 0h50","S533",253105,2),
            ("Banjar","2022/01/04","T2,4,2 7h13","H887",221614,1);
            
INSERT INTO STUDENT_SCORES (STU_ID,SUB_ID) VALUES (1,1),(1,2),(1,3),(1,4),(2,1),(2,2),(2,3),(2,4),(3,1),(3,2);






