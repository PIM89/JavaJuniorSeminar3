### Написать класс с двумя методами:
1. Принимает объекты, имплементирующие интерфейс serializable, и сохраняющие их в файл. Название файл - class.getName() + "_" + UUID.randomUUID().toString()
2. Принимает строку вида class.getName() + "_" + UUID.randomUUID().toString() и загружает объект из файла и удаляет этот файл.
* Что делать в ситуациях, когда файла нет или в нем лежит некорректные данные - подумать самостоятельно.