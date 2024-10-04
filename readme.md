## Запуск проекта

1. Клонируйте репозиторий:

    ```
    git clone https://github.com/KqartPro/bitlab-mvc-project.git
    ```
2. Откройте проект и настройте доступ к базе данных в
   файле [application.properties](src/main/resources/application.properties), например для Postgres:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/bitlab_project
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    ```
3. Перейдите в папку проекта и запустите:
    ```
    ./gradlew bootRun
    ```
   Для компьютеров с Windows используйте:
     ```
    gradlew bootRun
    ```
   Если вы открыли проект в IntelliJ IDEA вы можете использовать встроенную конфигурацию для запуска

4. Приложение будет доступно по адресу: http://localhost:8080.

