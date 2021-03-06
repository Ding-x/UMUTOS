package com.example.ding.umutos.presentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.ding.umutos.R;
import com.example.ding.umutos.business.AccessAccounts;
import com.example.ding.umutos.business.AccessBooks;
import com.example.ding.umutos.objects.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellerBookListActivity extends AppCompatActivity {

    private ListView bookList;
    private int bookID;
    private String bookTitle;
    private AccessBooks accessBookList;
    private AccessAccounts accessAccounts;
    private List<Book> newBookList;
    private TextView infoBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_booklist);
        bookList=(ListView)findViewById(R.id.sellerBookList);
        int bookImg[]={R.mipmap.book0,R.mipmap.book1,R.mipmap.book2,R.mipmap.book3,R.mipmap.book4,R.mipmap.book5,R.mipmap.book6,R.mipmap.book7,R.mipmap.book8,R.mipmap.book9,R.mipmap.book10};
        bookID=-1;
        accessBookList=new AccessBooks();
        accessAccounts=new AccessAccounts();
        newBookList=accessBookList.getUserBooks(2);
        infoBar=(TextView)findViewById(R.id.sellListInfoBar);
        infoBar.setText("Hi "+accessAccounts.getAccountByID(2).getUserName()+".");
        int size=newBookList.size();

        ArrayList<HashMap<String, Object>> books = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < size; i++) {
            HashMap<String, Object> book = new HashMap<String, Object>();
            book.put("id",""+newBookList.get(i).getBookID());
            book.put("img",bookImg[newBookList.get(i).getPicture()] );
            book.put("title", newBookList.get(i).getName());
            book.put("price","$"+newBookList.get(i).getPrice());
            books.add(book);
        }
        SimpleAdapter sItems = new SimpleAdapter(this,
                books,
                R.layout.activity_book_row,
                new String[] {"img", "title", "price","id" },

                new int[] { R.id.cusBookListImg, R.id.cusBookListTitle, R.id.cusBookListPrice , R.id.cusBookListID});

        bookList.setAdapter(sItems);

        bookList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                HashMap<String,String> map=(HashMap<String,String>)bookList.getItemAtPosition(arg2);
                String id=map.get("id");
                bookID=Integer.parseInt(id);
                bookTitle=map.get("title");
                infoBar.setText("You selected book: "+bookTitle);

            }

        });
    }

    public void buttonAddNewBook(View view) {
        openEditBookActivity();
    }

    public void buttonEditPostedBook(View view) {
        if (bookID<0)
            showEditDialog();
        else
            openEditBookActivity(bookID);
    }

    public void buttonDeletePostedBook(View view) {
        if (bookID<0)
            showDeleteDialog();
        else
            showDeleteDialog(bookTitle);
    }

    private void showDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert:")
                .setMessage("\n"+"Please select a book to delete.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {}
                })
                .show();
    }

    private void showDeleteDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation:")
                .setMessage("\n"+"Sure to delete "+msg+"?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        accessBookList.deleteBook(bookID);
                        finish();
                        Intent intent = new Intent(SellerBookListActivity.this, SellerBookListActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {}
                })
                .show();
    }

    private void showEditDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert:")
                .setMessage("\n"+"Please select a book to edit.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {}
                })
                .show();
    }

    private void openEditBookActivity(){
        Intent intent = new Intent(SellerBookListActivity.this,EditBookActivity.class);
        SellerBookListActivity.this.startActivity(intent);
    }

    private void openEditBookActivity(int bookID){
        Intent intent = new Intent(SellerBookListActivity.this,EditBookActivity.class);
        intent.putExtra("bookID", bookID);
        SellerBookListActivity.this.startActivity(intent);
    }



}
