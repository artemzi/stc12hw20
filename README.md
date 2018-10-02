# task description:

- Создать 2 сервлета, один из которых будет получать данные из БД, а другой сохранять данные из пользовательской формы в БД. Необходимо работать с многослойной архитектурой, логгерами. Тестами в данном задании код можно не покрывать (проект в дальнейшем будет дорабатываться на следующих двух занятиях).

App deployed to [Heroku](https://sheltered-bayou-62267.herokuapp.com/)

### TODO:
 - fix deploy
 before fix use:
 ```bash
heroku war:deploy build/libs/stc12hw20-1.0-SNAPSHOT.war --app sheltered-bayou-62267 HEROKU_DEBUG=1
```