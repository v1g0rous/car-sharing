package carsharing.view;

import java.util.List;

abstract public class AppMenu {

    List<MenuItem> menuItems;

    public static MenuItem getMenuItemByLabelNumber(int labelNumber, AppMenu menu) {
        MenuItem resultItem = null;

        List<MenuItem> items = menu.getMenuItems();
        for (MenuItem it : items) {
            if (it.getLabelNumber() == labelNumber) {
                resultItem = it;
            }
        }
        return resultItem;
    }


    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void show() {
        menuItems.sort(new MenuItem.DisplayOrderComparator());

        for (MenuItem item : menuItems) {
            System.out.println(item.getLabelNumber() + ". " + item.getLabel());
        }
    }

    public void show(List<MenuItem> items) {
        items.sort(new MenuItem.DisplayOrderComparator());

        for (MenuItem item : items) {
            System.out.println(item.getLabelNumber() + ". " + item.getLabel());
        }
    }

}
