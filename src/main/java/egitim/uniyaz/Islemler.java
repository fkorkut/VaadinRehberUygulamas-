package egitim.uniyaz;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import egitim.uniyaz.db.DBTransaction;
import egitim.uniyaz.domain.Kisi;

import java.util.ArrayList;
import java.util.List;

class Islemler {
    TextField nameField, surnameField, phoneField;
    Button save, delete,update;
    Table table;
    IndexedContainer indexedContainer;
     static List<Kisi> listKisi;


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
                saveButtonClick();
            }
        });
        formLayout.addComponent(save);

        table = new Table("Kişiler");
        table.setSelectable(true);
        table.addItemClickListener(new ItemClickEvent.ItemClickListener() { //tablodan bir satır seçildiğinde
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
              clickTable(itemClickEvent);

                Kisi kisi=(Kisi)itemClickEvent.getItemId();
                delete.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        deleteButtonClick(kisi.getId());
                    }
                });
                update.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        updateButton(kisi);
                    }
                });

            }
        });
        createContainer();
        insertTable();
        table.setPageLength(table.size());
        formLayout.addComponent(table);

        update = new Button();
        update.setCaption("Güncelle");
        formLayout.addComponent(update);
        delete = new Button();
        delete.setCaption("Sil");
         formLayout.addComponent(delete);


    }

    public void saveButtonClick() {
        Kisi kisi = new Kisi();
        kisi.setAd(nameField.getValue());
        kisi.setSoyad(surnameField.getValue());
        kisi.setTelefon(phoneField.getValue());

        DBTransaction dbTransaction = new DBTransaction();
        int sonuc = dbTransaction.addKisi(kisi);
        if (sonuc > 0) Notification.show("Kişi Eklendi");
    }

    public void createContainer() {
        indexedContainer = new IndexedContainer();
        indexedContainer.addContainerProperty("id", Integer.class, 0);
        indexedContainer.addContainerProperty("adi", String.class, null);
        indexedContainer.addContainerProperty("soyadi", String.class, null);
        indexedContainer.addContainerProperty("Telefon", String.class, null);
    }

    public void insertTable() {
        DBTransaction db = new DBTransaction();
        listKisi = new ArrayList<>();
        listKisi = db.listKisi();
       for (Kisi kisi : listKisi) {//tabloların içine verileri ekleme
            Item item = indexedContainer.addItem(kisi);
            item.getItemProperty("id").setValue(kisi.getId());
            item.getItemProperty("adi").setValue(kisi.getAd());
            item.getItemProperty("soyadi").setValue(kisi.getSoyad());
            item.getItemProperty("Telefon").setValue(kisi.getTelefon());
        }

        table.setContainerDataSource(indexedContainer);
    }

    public void clickTable(ItemClickEvent itemClickEvent){//tabloda tıklanan satırı kisi nesneleri alır
        String adi = (String) itemClickEvent.getItem().getItemProperty("adi").getValue();
        nameField.setValue(adi);
        String soyadi = (String) itemClickEvent.getItem().getItemProperty("soyadi").getValue();
        surnameField.setValue(soyadi);
        String telefon=(String) itemClickEvent.getItem().getItemProperty("Telefon").getValue();
        phoneField.setValue(telefon);
        int id=(int) itemClickEvent.getItem().getItemProperty("id").getValue();
    }


    public void deleteButtonClick(int id){
         DBTransaction dbTransaction = new DBTransaction();
         dbTransaction.deleteKisi(id);
         Notification.show(id+"Kişi Silindi");

    }


    public  void updateButton(Kisi kisi){
        DBTransaction dbTransaction = new DBTransaction();
        kisi.setAd(nameField.getValue());
        kisi.setSoyad(surnameField.getValue());
        kisi.setTelefon(phoneField.getValue());
        int sonuc=dbTransaction.update(kisi);
        if (sonuc>0) Notification.show("Kişi güncellendi");
    }
}
