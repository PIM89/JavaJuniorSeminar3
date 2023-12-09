package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Ilya", 20);

        Homework homework = new Homework();
        homework.saveObjInFile(person);

        Person person1 = homework.loadObjAndDelFile("class org.example.Person_173bb2ff-e6b7-430b-bd34-a9f06e70f2a2");
        System.out.println(person1);
    }
}


class Homework {
    /**
     * Метод принимает объект, имплементирующий интерфейс Serializable и сохраняет его в директорию "SaveObjDir"
     * @param obj
     * @param <T>
     */
    public <T extends Serializable> void saveObjInFile(T obj) {
        File saveDir = new File("SaveObjDir");
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        Path pathFile = Path.of(saveDir.getAbsolutePath() + File.separator + obj.getClass() + "_" + UUID.randomUUID().toString());

        try (OutputStream outputStream = Files.newOutputStream(pathFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод принимает строку вида class.getName() + "_" + UUID.randomUUID().toString(),
     * проверяет наличие файла с таким именем в директории "SaveObjDir" и десериализирует
     * его в объект Person. Возвращает объект типа Person при этом удаляет исходный файл.
     * @param nameFileObj Имя файла
     * @return Person
     */

    public Person loadObjAndDelFile(String nameFileObj) {
        File file = new File("SaveObjDir");

        for (File listFile : file.listFiles()) {
            if (listFile.getName().equals(nameFileObj)) {
                Person person = null;
                Path path = Path.of(file.getAbsolutePath() + File.separator + nameFileObj);
                try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
                    person = (Person) objectInputStream.readObject();
                    listFile.delete();
                } catch (InvalidClassException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return person;
            }
        }
        System.out.println("Файл не найден!");
        return null;
    }
}

class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}