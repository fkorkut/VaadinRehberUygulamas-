package egitim.uniyaz;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import egitim.uniyaz.db.DBOperation;
import egitim.uniyaz.domain.Kisi;

import java.util.ArrayList;
import java.util.List;

class Islemler {
    TextField nameField, surnameField, phoneField ;
    Button save, delete,update;
    Table table;
    IndexedContainer indexedContainer;
    List<Kisi> listKisi;
    Kisi kisi;
    DBOperation dbOperation = new DBOperation();

    public void components(FormLayout formLayout) {

        nameField = new TextField();
        nameField.setCaption("Adı");
        formLayout.addComponent(nameField);

        surnameField = new TextField();
        surnameField.setCaption("Soyadı");
        formLayout.addComponent(surnameField);

        phoneField = new TextField();
        phoneField.setCaption("Telefon");
        formLayout.addComponent(phoneField);

        save = new Button();
        save.setCaption("KAYDET");
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                saveButton();
            }
        });
        formLayout.addComponent(save);

        table = new Table("Kişiler");
        table.setSelectable(true);
        table.addItemClickListener(new ItemClickEvent.ItemClickListener() { //tablodan bir satır seçildiğinde
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
              clickTable(itemClickEvent);
                kisi=(Kisi)itemClickEvent.getItemId();
            }
        });
        createContainer();
        insertTable();
        table.setPageLength(table.size());
        formLayout.addComponent(table);

        update = new Button();
        update.setCaption("Güncelle");
        update.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
              updateButton();
            }
        });
        formLayout.addComponent(update);
        delete = new Button();
        delete.setCaption("Sil");
        delete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                deleteButton();
            }
        });
        formLayout.addComponent(delete);


    }


    private void saveButton() {
        kisi = new Kisi();
        kisi.setAd(nameField.getValue());
        kisi.setSoyad(surnameField.getValue());
        kisi.setTelefon(phoneField.getValue());
        for (Kisi kisiFor:listKisi) {
            if (kisiFor.getTelefon()==kisi.getTelefon()){
                Notification.show("Ekleme yapılamadı aynı telefon ile daha önce kayıt yapıldı");
                return;
            }
        }

        int sonuc = dbOperation.addKisi(kisi);
        if (sonuc > 0) Notification.show("Kişi Eklendi");

    }

    private void createContainer() {
        indexedContainer = new IndexedContainer();
        indexedContainer.addContainerProperty("id", Integer.class, 0);
        indexedContainer.addContainerProperty("adi", String.class, null);
        indexedContainer.addContainerProperty("soyadi", String.class, null);
        indexedContainer.addContainerProperty("Telefon", String.class, null);
    }

    private void insertTable() {
        listKisi = new ArrayList<>();
        listKisi = dbOperation.listKisi();
       for (Kisi kisi : listKisi) {//tabloların içine verileri ekleme
            Item item = indexedContainer.addItem(kisi);
            item.getItemProperty("id").setValue(kisi.getId());
            item.getItemProperty("adi").setValue(kisi.getAd());
            item.getItemProperty("soyadi").setValue(kisi.getSoyad());
            item.getItemProperty("Telefon").setValue(kisi.getTelefon());
        }

        table.setContainerDataSource(indexedContainer);
    }

    private void clickTable(ItemClickEvent itemClickEvent){//tabloda tıklanan satırı kisi nesneleri alır
        String adi = (String) itemClickEvent.getItem().getItemProperty("adi").getValue();
        nameField.setValue(adi);
        String soyadi = (String) itemClickEvent.getItem().getItemProperty("soyadi").getValue();
        surnameField.setValue(soyadi);
        String telefon=(String) itemClickEvent.getItem().getItemProperty("Telefon").getValue();
        phoneField.setValue(telefon);
    }
    private void updateButton() {
        kisi.setAd(nameField.getValue());
        kisi.setSoyad(surnameField.getValue());
        kisi.setTelefon(phoneField.getValue());
        int sonuc= dbOperation.update(kisi);
        if (sonuc>0) Notification.show("Kişi güncellendi");
    }

    private void deleteButton() {
        dbOperation.deleteKisi(kisi.getId());
        Notification.show(kisi.getId()+"idli Kişi Silindi");
    }




}
