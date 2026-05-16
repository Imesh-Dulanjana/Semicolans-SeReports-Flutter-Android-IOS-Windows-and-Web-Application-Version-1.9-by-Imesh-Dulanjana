// ignore_for_file: use_key_in_widget_constructors, use_build_context_synchronously

import 'package:flutter/material.dart';
import 'package:sereports/constants.dart';
import 'package:sereports/repository/auth_repo.dart';
import 'package:sereports/screen/auth_screen/login.dart';
import 'package:sereports/screen/banking/bank.dart';
import 'package:sereports/screen/customers/customer.dart';
import 'package:sereports/screen/dashboard/dashbaord.dart';
import 'package:sereports/screen/home/home_screen.dart';
import 'package:sereports/screen/income/expences/income_and_expences.dart';
import 'package:sereports/screen/invoice/invoice_creating.dart';
import 'package:sereports/screen/product/product_record.dart';
import 'package:sereports/screen/purchase/purchase.dart';
import 'package:sereports/screen/sales/sales.dart';
import 'package:sereports/screen/supplier/supplier.dart';
import 'package:shared_preferences/shared_preferences.dart';

/// App-wide side drawer.
/// Previously hidden navigation items have been fully restored.
class AppDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.only(
        topRight: Radius.circular(radiusValue),
        bottomRight: Radius.circular(radiusValue),
      ),
      child: Drawer(
        backgroundColor: Colors.white,
        child: Column(
          children: [
            const SizedBox(height: 20),
            Expanded(
              child: ListView(
                children: [
                  // Home
                  ListTile(
                    leading: const Icon(Icons.home),
                    title: const Text('Home'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => HomeScreen()));
                    },
                  ),

                  // Dashboard
                  ListTile(
                    leading: const Icon(Icons.dashboard),
                    title: const Text('Dashboard'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const DashbaordScreen()));
                    },
                  ),

                  // Invoice Creating
                  ListTile(
                    leading: const Icon(Icons.receipt_long),
                    title: const Text('Invoice Creating'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) =>
                              const InvoiceCreationScreen()));
                    },
                  ),

                  // Products
                  ListTile(
                    leading: const Icon(Icons.inventory),
                    title: const Text('Products'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const ProductRecordsPage()));
                    },
                  ),

                  // Suppliers
                  ListTile(
                    leading: const Icon(Icons.local_shipping),
                    title: const Text('Suppliers'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const SupplierPage()));
                    },
                  ),

                  // Customers
                  ListTile(
                    leading: const Icon(Icons.people),
                    title: const Text('Customers'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const CustomerPage()));
                    },
                  ),

                  // Sales
                  ListTile(
                    leading: const Icon(Icons.trending_up),
                    title: const Text('Sales'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const SalesPage()));
                    },
                  ),

                  // Purchase
                  ListTile(
                    leading: const Icon(Icons.shopping_cart),
                    title: const Text('Purchase'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const PurchasePage()));
                    },
                  ),

                  // Income / Expenses
                  ListTile(
                    leading: const Icon(Icons.attach_money),
                    title: const Text('Income/Expenses'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const IncomeAndExpences()));
                    },
                  ),

                  // Banking
                  ListTile(
                    leading: const Icon(Icons.account_balance),
                    title: const Text('Banking'),
                    onTap: () {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const BankPage()));
                    },
                  ),

                  const Divider(),

                  // Logout — clears token and returns to Login screen
                  ListTile(
                    leading: const Icon(Icons.logout, color: Colors.red),
                    title: const Text('Logout',
                        style: TextStyle(color: Colors.red)),
                    onTap: () async {
                      SharedPreferences preferences =
                          await SharedPreferences.getInstance();
                      AuthRepo authRepo = AuthRepo(preferences);
                      await authRepo.logout();
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => const LoginScreen()));
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
