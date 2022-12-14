import manager.FileBackedTasksManager;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        //TaskManager manager = Managers.getDefault();
//        Epic shop = new Epic("Магазин", "Покупка продуктов");
//        manager.createEpic(shop);
//
//        Subtask sub1 = new Subtask("Еда", "купить свеклу", shop.getId());
//        Subtask sub2 = new Subtask("Напитки", "купить воды", shop.getId());
//        manager.createSubtask(sub1);
//        manager.createSubtask(sub2);
//
//        Epic sport = new Epic("Спорт", "Записаться в спортзал");
//        manager.createEpic(sport);
//        Subtask sub3 = new Subtask("Справка", "Получить справку от врача", sport.getId());
//        Subtask sub4 = new Subtask("Абонемент", "Купить абонемент", Status.IN_PROGRESS, sport.getId());
//        manager.createSubtask(sub3);
//        manager.createSubtask(sub4);

        //System.out.println(manager.getEpic(sport.getId()));
        //System.out.println(manager.getEpic(shop.getId()));

//        manager.getSubtask(sub1.getId());
//        manager.getSubtask(sub2.getId());
//        manager.getSubtask(sub3.getId());
//        manager.getSubtask(sub4.getId());
//        manager.getEpic(shop.getId());
        //manager.getEpic(sport.getId());
        //manager.getSubtask(sub1.getId());
        //manager.getSubtask(sub2.getId());
        //System.out.println(manager.getHistory());

        //manager.getEpic(sport.getId());
        //System.out.println(manager.getHistory());
        //manager.removeEpic(shop.getId());
        //System.out.println(manager.getHistory());

        //fromFile
        FileBackedTasksManager fromFile = FileBackedTasksManager
                .loadFromFile(new File("C:\\Users\\abyss\\OneDrive\\Рабочий стол\\test\\loadFile.csv"));
        fromFile.save();
    }
}