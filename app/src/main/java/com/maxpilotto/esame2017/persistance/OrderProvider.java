package com.maxpilotto.esame2017.persistance;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maxpilotto.esame2017.persistance.tables.OrderDetailTable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;
import com.maxpilotto.esame2017.persistance.tables.ProductTable;

public class OrderProvider extends ContentProvider {
    public static final String AUTHORITY = "com.maxpilotto.esame2017.database.ContentProvider";

    public static final String BASE_PATH_ORDERS = "orders";
    public static final String BASE_PATH_PRODUCTS = "products";
    public static final String BASE_PATH_ORDER_DETAILS = "order_products";

    public static final int ALL_PRODUCTS = 100;
    public static final int ALL_ORDERS = 200;
    public static final int ALL_ORDER_DETAILS = 300;
    public static final int SINGLE_PRODUCT = 120;
    public static final int SINGLE_ORDER = 220;
    public static final int SINGLE_ORDER_DETAIL = 320;

    public static final String MIME_TYPE_ALL_PRODUCTS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_products";
    public static final String MIME_TYPE_ALL_ORDERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_orders";
    public static final String MIME_TYPE_ALL_ORDER_DETAILS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_order_products";
    public static final String MIME_TYPE_PRODUCT = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_products";
    public static final String MIME_TYPE_ORDER = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.all_orders";
    public static final String MIME_TYPE_ORDER_DETAIL = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_order_products";

    public static final Uri URI_PRODUCTS = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_PRODUCTS);
    public static final Uri URI_ORDERS = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_ORDERS);
    public static final Uri URI_ORDER_DETAILS = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_ORDER_DETAILS);

    private DatabaseHelper database;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH_PRODUCTS, ALL_PRODUCTS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_PRODUCTS + "/#", SINGLE_PRODUCT);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_ORDERS, ALL_ORDERS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_ORDERS + "/#", SINGLE_ORDER);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_ORDER_DETAILS, ALL_ORDER_DETAILS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_ORDER_DETAILS + "/#", SINGLE_ORDER_DETAIL);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = database.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case SINGLE_PRODUCT:
                builder.setTables(ProductTable.TABLE_NAME);
                builder.appendWhere(ProductTable._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_PRODUCTS:
                builder.setTables(ProductTable.TABLE_NAME);
                break;

            case SINGLE_ORDER:
                builder.setTables(OrderTable.TABLE_NAME);
                builder.appendWhere(OrderTable._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_ORDERS:
                builder.setTables(OrderTable.TABLE_NAME);
                break;

            case SINGLE_ORDER_DETAIL:
                builder.setTables(OrderDetailTable.TABLE_NAME);
                builder.appendWhere(OrderDetailTable._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_ORDER_DETAILS:
                builder.setTables(OrderDetailTable.TABLE_NAME);
                break;
        }

        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SINGLE_PRODUCT:
                return MIME_TYPE_PRODUCT;

            case ALL_PRODUCTS:
                return MIME_TYPE_ALL_PRODUCTS;

            case SINGLE_ORDER:
                return MIME_TYPE_ORDER;

            case ALL_ORDERS:
                return MIME_TYPE_ALL_ORDERS;

            case SINGLE_ORDER_DETAIL:
                return MIME_TYPE_ORDER_DETAIL;

            case ALL_ORDER_DETAILS:
                return MIME_TYPE_ALL_ORDER_DETAILS;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == ALL_PRODUCTS) {
            long result = Database.get().insert(values, ProductTable.TABLE_NAME);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_PRODUCTS + "/" + result;

            getContext().getContentResolver().notifyChange(uri, null);

            return Uri.parse(resultString);
        } else if (uriMatcher.match(uri) == ALL_ORDERS) {
            long result = Database.get().insert(values, OrderTable.TABLE_NAME);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_ORDERS + "/" + result;

            getContext().getContentResolver().notifyChange(uri, null);

            return Uri.parse(resultString);
        } else if (uriMatcher.match(uri) == ALL_ORDER_DETAILS) {
            long result = Database.get().insert(values, OrderDetailTable.TABLE_NAME);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_ORDER_DETAILS + "/" + result;

            getContext().getContentResolver().notifyChange(uri, null);

            return Uri.parse(resultString);
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_PRODUCT:
                table = ProductTable.TABLE_NAME;
                query = ProductTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_PRODUCTS:
                table = ProductTable.TABLE_NAME;
                query = selection;
                break;

            case SINGLE_ORDER:
                table = OrderTable.TABLE_NAME;
                query = OrderTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_ORDERS:
                table = OrderTable.TABLE_NAME;
                query = selection;
                break;

            case SINGLE_ORDER_DETAIL:
                table = OrderDetailTable.TABLE_NAME;
                query = OrderDetailTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_ORDER_DETAILS:
                table = OrderDetailTable.TABLE_NAME;
                query = selection;
                break;
        }

        int deletedRows = db.delete(table, query, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_PRODUCT:
                table = ProductTable.TABLE_NAME;
                query = ProductTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_PRODUCTS:
                table = ProductTable.TABLE_NAME;
                query = selection;
                break;

            case SINGLE_ORDER:
                table = OrderTable.TABLE_NAME;
                query = OrderTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_ORDERS:
                table = OrderTable.TABLE_NAME;
                query = selection;
                break;

            case SINGLE_ORDER_DETAIL:
                table = OrderDetailTable.TABLE_NAME;
                query = OrderDetailTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_ORDER_DETAILS:
                table = OrderDetailTable.TABLE_NAME;
                query = selection;
                break;
        }

        int updatedRows = db.update(table, values, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;
    }
}
