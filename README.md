# Как запустить автотесты:
1. **Загрузить копию репозитория на локальный компьютер.**

2. **Запустить image в Docker (работает на порту 3306):**
> docker-compose up

3. **Запустить сервис для тестирования (работает на порту 8080):** 
> java -jar [aqa-shop.jar](aqa-shop.jar)

4. **(При отсутствии Node.js на локальном компьютере) Установить Node.js с [официального сайта](https://nodejs.org/en/download).**

5. **В терминале каталога [gate-simulator](gate-simulator) ввести команду:**
> npm start -port 9999 

6. **В терминале каталога [ReportPortal](ReportPortal) ввести команду (на территории России необходимо включить VPN!):**
> docker-compose -p reportportal up -d --force-recreate

7. **Пройти по ссылке [localhost:8082](http://localhost:8082/ui/#login) и войти в систему по данным:**
> login: superadmin
> 
> pass: erebus

8. **Заменить данные в файле [reportportal.properties](src%2Ftest%2Fresources%2Freportportal.properties) данными со [страницы](http://localhost:8082/ui/#user-profile):**

![данные](https://github.com/MeritRa/diploma/assets/113490038/d0c128cb-e6d2-4ffb-b9fc-5b09ab74d088)

9. **Запустить автотесты в IDEA.**

10. **В терминале репозитория ввести команду для генерации отчета Allure:**

> .\gradlew allureReport