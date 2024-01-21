insert into sample_spring_db.kafka_message_template(kafka_message_template_id, message, message_name) values (null, "Hello, my name is ${name}", "My name");
insert into sample_spring_db.kafka_message_template(kafka_message_template_id, message, message_name) values (null, "My occupation is ${occupation}", "My occupation");
insert into sample_spring_db.kafka_message_template(kafka_message_template_id, message, message_name) values (null, "Hello, age is ${age}", "My age");

Select * from sample_spring_db.kafka_message_template;