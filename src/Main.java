public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Epic shop = new Epic("Магазин", "Покупка продуктов");
        manager.createEpic(shop);

        Subtask sub1 = new Subtask("Еда", "купить свеклу", shop.getId());
        Subtask sub2 = new Subtask("Напитки", "купить воды", shop.getId());
        manager.createSubtask(sub1);
        manager.createSubtask(sub2);

        System.out.println(sub1);
        System.out.println(sub2);

        Epic sport = new Epic("Спорт", "Записаться в спортзал");
        manager.createEpic(sport);

        manager.createSubtask(new Subtask("Справка", "Получить справку от врача", sport.getId()));
        manager.createSubtask(new Subtask("Абонемент", "Купить абонемент", Status.IN_PROGRESS, sport.getId()));
        System.out.println(manager.getById(sport.getId()));

        System.out.println(manager.getById(shop.getId()));
        manager.removeById(shop.getId());
        System.out.println(manager.getById(shop.getId()));
        manager.removeAllSubtasks();
        manager.removeAllEpics();
        System.out.println(manager.getById(shop.getId()));

    }
}
