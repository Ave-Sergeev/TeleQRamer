# TeleQRamer[Русская версия](README.ru.md)This project is a game project and should not be considered as a serious development.It is presented in the form of `Telegram Bot` creating on request QR code and shortURL, as well as a server (HTTP) for redirect to the original URLs.The service provides the functionality of:1) QR code generation by provided URL (library [QRGen](https://github.com/kenglxn/QRGen) is used).   The default size of the generated JPG is 250x250 (can be changed in the service configuration).2) Generation of shortURL for the provided URL. When using shortURL (following the link) a redirect to the original URL is performed.   Token is implemented on [ULID](https://github.com/ulid/spec). Token -> URL mapping is stored in Redis (SingleNode).   For low-load usage, this implementation is sufficient, and it will guarantee (conditionally) the uniqueness of the keys due to the ULID features.P.S. Important clarification:In reality, a completely different architecture and technologies will be used for a combat project. Please do not take the game project seriously.P.S. Don't forget to leave a ⭐ if you found this project useful.