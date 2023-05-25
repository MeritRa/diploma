package ru.netology.data;

import lombok.SneakyThrows;
import lombok.var;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static DataHelper.OrderStatus getApprovedCreditOrderStatus() {
        var selectSQL = "SELECT status FROM credit_request_entity INNER JOIN order_entity ON credit_request_entity.id = order_entity.id WHERE bank_id=credit_id ORDER BY credit_request_entity.created DESC LIMIT 1";
        try (var conn = getConn()) {
            var status = runner.query(conn, selectSQL, new ScalarHandler<String>());
            return new DataHelper.OrderStatus(status);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static DataHelper.OrderStatus getDeclinedCreditOrderStatus() {
        var selectSQL = "SELECT status FROM credit_request_entity INNER JOIN order_entity ON credit_request_entity.id = order_entity.id WHERE bank_id<>credit_id ORDER BY credit_request_entity.created DESC LIMIT 1";
        try (var conn = getConn()) {
            var status = runner.query(conn, selectSQL, new ScalarHandler<String>());
            return new DataHelper.OrderStatus(status);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static DataHelper.OrderStatus getApprovedPaymentOrderStatus() {
        var selectSQL = "SELECT status FROM payment_entity INNER JOIN order_entity ON payment_entity.id=order_entity.id WHERE transaction_id=payment_id ORDER BY payment_entity.created DESC LIMIT 1";
        try (var conn = getConn()) {
            var status = runner.query(conn, selectSQL, new ScalarHandler<String>());
            return new DataHelper.OrderStatus(status);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static DataHelper.OrderStatus getDeclinedPaymentOrderStatus() {
        var selectSQL = "SELECT status FROM payment_entity INNER JOIN order_entity ON payment_entity.id = order_entity.id WHERE transaction_id<>payment_id ORDER BY payment_entity.created DESC LIMIT 1";
        try (var conn = getConn()) {
            var status = runner.query(conn, selectSQL, new ScalarHandler<String>());
            return new DataHelper.OrderStatus(status);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getPaymentAmount() {
        var selectSQL = "SELECT amount FROM payment_entity WHERE status='APPROVED' ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            return runner.query(conn, selectSQL, new ScalarHandler<String>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var conn = getConn();
        runner.execute(conn, "DELETE FROM credit_request_entity");
        runner.execute(conn, "DELETE FROM payment_entity");
        runner.execute(conn, "DELETE FROM order_entity");
    }
}