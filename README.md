# API de Contacto GDN
Este repositorio contiene una API de Contacto desarrollada en Java con Spring Boot y Spring Security. 
La API utiliza OpenSSL para la generación de claves privadas y públicas, así como JWT para la autenticación.
### Tecnologías utilizadas

- Spring Boot: Framework de aplicación de Java para crear aplicaciones autónomas y basadas en producción.
- Spring Security: Marco de seguridad que proporciona autenticación y control de acceso para aplicaciones Spring.
- JavaMail: Biblioteca de Java para enviar correos electrónicos.
- Thymeleaf: motor de plantillas de XML/XHTML/HTML5
- OpenSSL: Herramienta utilizada para la generación de claves privadas y públicas.
- JWT (JSON Web Tokens): Estándar para la autenticación y autorización de usuarios.
- UUID (Universally Unique Identifier): Utilizado para generar identificadores únicos, como las URL de confirmación de correo electrónico.

# Generación de claves privadas y públicas
Para utilizar la funcionalidad de autenticación JWT, es necesario generar un par de claves privadas y públicas. Puedes seguir estos pasos para generarlas:

# Abre una terminal y ejecuta los siguientes comandos:

openssl genrsa -out private.key 2048

openssl rsa -in private.key -pubout -out public.key

Esto generará un archivo private.key que contiene la clave privada y un archivo public.key que contiene la clave pública.

# Configuración para el envío de correos electrónicos

Configuración de propiedades para el envío de correos
La API también puede enviar correos electrónicos a través de un proveedor de servicios de correo electrónico. Para configurar esta funcionalidad, asegúrate de tener las siguientes propiedades en tu archivo de configuración application.properties:

properties

spring.mail.host=smtp.example.com

spring.mail.port=587

spring.mail.username=your_email@example.com

spring.mail.password=your_email_password

spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.starttls.enable=true

Reemplaza smtp.example.com con el servidor SMTP de tu proveedor de correo electrónico, your_email@example.com con tu dirección de correo electrónico y your_email_password con tu contraseña de correo electrónico.

Ejecutar la aplicación
Una vez generadas las claves y configuradas las propiedades de correo electrónico, 
puedes ejecutar la aplicación utilizando Maven o Gradle. Por ejemplo, con Maven puedes usar el siguiente comando:

mvn spring-boot:run

Esto iniciará la aplicación en el puerto predeterminado (generalmente el puerto 8080).

¡Ahora estás listo para comenzar a usar la API de Contacto GDN! Si tienes alguna pregunta o problema, no dudes en abrir un issue en este repositorio. ¡Gracias por usar nuestra API!






