// ignore_for_file: use_build_context_synchronously

import 'dart:async';

import 'package:flutter/material.dart';
import 'package:sereports/repository/auth_repo.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SessionManager {
  final SharedPreferences _preferences;
  final BuildContext context;
  Timer? _sessionCheckTimer;

  SessionManager(this._preferences, this.context);

  void startSessionMonitoring() {
    _sessionCheckTimer = Timer.periodic(Duration(minutes: 1), (_) async {
      final authRepo = AuthRepo(_preferences);
      final isValid = await authRepo.isLoggedIn();

      if (!isValid && mounted) {
        authRepo.logout(context);
        stopSessionMonitoring();
      }
    });
  }

  void stopSessionMonitoring() {
    _sessionCheckTimer?.cancel();
    _sessionCheckTimer = null;
  }

  bool get mounted => context.mounted;
}