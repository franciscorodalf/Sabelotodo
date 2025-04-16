-- Categorías
INSERT INTO categorias (nombre) VALUES ('Ciencia');
INSERT INTO categorias (nombre) VALUES ('Historia');
INSERT INTO categorias (nombre) VALUES ('Geografía');
INSERT INTO categorias (nombre) VALUES ('Literatura');
INSERT INTO categorias (nombre) VALUES ('Arte');
INSERT INTO categorias (nombre) VALUES ('Entretenimiento');

-- Preguntas por categoría

-- Ciencia
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Cuál es el planeta más cercano al Sol?', 'Venus', 'Marte', 'Mercurio', 'Júpiter', 'C', 1);

INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Qué tipo de célula no tiene núcleo?', 'Animal', 'Vegetal', 'Procariota', 'Eucariota', 'C', 1);

-- Historia
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿En qué año terminó la Segunda Guerra Mundial?', '1945', '1939', '1942', '1950', 'A', 2);

INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Quién fue el primer emperador romano?', 'Julio César', 'Augusto', 'Nerón', 'Trajano', 'B', 2);

-- Geografía
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Cuál es la capital de Australia?', 'Sídney', 'Melbourne', 'Canberra', 'Brisbane', 'C', 3);

INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Qué continente es el más grande?', 'África', 'Asia', 'América', 'Europa', 'B', 3);

-- Literatura
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Quién escribió Don Quijote?', 'Gabriel García Márquez', 'Miguel de Cervantes', 'Pablo Neruda', 'Jorge Luis Borges', 'B', 4);

INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Qué novela empieza con “Llamadme Ismael”?', 'Drácula', 'Moby Dick', 'Frankenstein', '1984', 'B', 4);

-- Arte
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Quién pintó la Mona Lisa?', 'Van Gogh', 'Picasso', 'Leonardo da Vinci', 'Rembrandt', 'C', 5);

INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Qué corriente artística es Salvador Dalí?', 'Surrealismo', 'Impresionismo', 'Cubismo', 'Realismo', 'A', 5);

-- Entretenimiento
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿En qué película aparece el personaje Frodo?', 'Harry Potter', 'El Señor de los Anillos', 'Star Wars', 'Matrix', 'B', 6);

INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, categoria_id)
VALUES ('¿Cuál es el nombre del padre de Simba?', 'Scar', 'Timon', 'Mufasa', 'Rafiki', 'C', 6);
