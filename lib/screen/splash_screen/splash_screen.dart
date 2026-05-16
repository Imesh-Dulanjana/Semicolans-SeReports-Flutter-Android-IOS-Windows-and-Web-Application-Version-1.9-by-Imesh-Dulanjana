// ignore_for_file: unused_local_variable, avoid_unnecessary_containers, use_build_context_synchronously

import 'package:flutter/material.dart';
import 'package:sereports/constants.dart';
import 'package:sereports/repository/auth_repo.dart';
import 'package:sereports/screen/auth_screen/login.dart';
import 'package:sereports/screen/dashboard/dashbaord.dart';
import 'package:shared_preferences/shared_preferences.dart';

/// Splash screen shown for 3 seconds on app launch.
/// After the delay it checks for an existing valid JWT:
///   - Logged in  → navigate to DashbaordScreen
///   - Not logged in → navigate to LoginScreen
class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
  }

  Future<void> _checkLoginStatus() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    AuthRepo authRepo = AuthRepo(preferences);

    // Show splash screen for 3 seconds
    await Future.delayed(const Duration(seconds: 3));

    // Check if user has a valid, non-expired token
    bool isLoggedIn = await authRepo.isLoggedIn();

    if (mounted) {
      if (isLoggedIn) {
        // Token exists — go directly to Dashboard
        Navigator.of(context).pushReplacement(
            MaterialPageRoute(builder: (context) => const DashbaordScreen()));
      } else {
        // No token — show Login screen
        Navigator.of(context).pushReplacement(
            MaterialPageRoute(builder: (context) => const LoginScreen()));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: kBackgroundColor,
      body: Container(
        child: Column(
          children: <Widget>[
            Expanded(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Center(
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: SizedBox(
                        child: Image.asset(
                          'assets/logo.png',
                          fit: BoxFit.cover,
                          gaplessPlayback: true,
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(bottom: 28),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: const [
                  Text(
                    'Version : 1.0.0',
                    style: TextStyle(
                        fontSize: 12,
                        fontWeight: FontWeight.w500,
                        color: Colors.black),
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
