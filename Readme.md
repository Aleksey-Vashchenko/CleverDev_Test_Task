##Структура
[OldSystem](OldSystem) - имитация старой системы с генератором доп данных
[src](src) - реализация сервиса для импорта

##Запуск
Запуск через [docker-compose.yml](docker-compose.yml)

##Принцип работы
[ScheduledImportTask.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Fcleverdev_test_task%2FScheduledImportTask.java) обращается к [ImportService.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Fcleverdev_test_task%2Fservice%2FImportService.java) для выполнения импорта с заданной cron выражением частотой.
[ImportService.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Fcleverdev_test_task%2Fservice%2FImportService.java) получает список всех клиентов с помощью [ClientDataFetcher.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Fcleverdev_test_task%2Ffetchers%2FClientDataFetcher.java).
После для каждого клиента запускает задачу [ClientImportTask.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Fcleverdev_test_task%2Fservice%2Ftask%2FClientImportTask.java), которая с помощью [NotesDataFetcher.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Fcleverdev_test_task%2Ffetchers%2FNotesDataFetcher.java) получает все заметки клиента за промежуток 200 лет (было выбрано данное значение, чтобы наверянка захватить все существующие заметки)).
Каждый клиент проверяется на соответствие условия импорта заметок. В случае соответствия начинается проверка заметок и, при надобности, их сохранение или обновление, а также создание отсутствующих в базе пользователей.