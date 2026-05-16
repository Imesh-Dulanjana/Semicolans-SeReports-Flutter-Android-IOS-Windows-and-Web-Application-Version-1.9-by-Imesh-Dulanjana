import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:http_interceptor/http/intercepted_http.dart';
import 'package:http_interceptor/http_interceptor.dart';
import 'package:sereports/constants.dart';
import 'package:sereports/repository/auth_repo.dart';
import 'package:sereports/utils/interceptor.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ApiException implements Exception {
  ApiException(this.errorMessage);
  String errorMessage;
  @override
  String toString() => errorMessage;
}

class LoginResult {
  final bool success;
  final String? token;
  final String? errorMessage;
  const LoginResult({required this.success, this.token, this.errorMessage});
  const LoginResult.ok(String token) : success = true, token = token, errorMessage = null;
  const LoginResult.fail(String message) : success = false, token = null, errorMessage = message;
}

class Api {
  static String loginUrl = "${baseUrl}auth/login";
  static String userPermissions = "${baseUrl}auth/user-permissions";

  static Future<Map<String, dynamic>> get({
    required final String url,
    required final Map<String, String> parameter,
  }) async {
    try {
      final interceptedHttp = InterceptedHttp.build(interceptors: [SeReportInterceptor()]);
      final response = await interceptedHttp.get(url.toUri(), params: parameter);
      if (response.statusCode == 200) {
        return Map<String, dynamic>.from(jsonDecode(response.body));
      } else {
        return Future.error("Error: ${response.statusCode}", StackTrace.fromString(response.body));
      }
    } on SocketException {
      return Future.error('No Internet connection');
    } catch (e) {
      return Future.error('Unexpected error: $e');
    }
  }

  static Future<Map<String, dynamic>> post({
    required final String url,
    required final Map<String, dynamic> body,
  }) async {
    try {
      final interceptedHttp = InterceptedHttp.build(interceptors: [SeReportInterceptor()]);
      final response = await interceptedHttp.post(
        url.toUri(),
        body: jsonEncode(body),
        headers: {'Content-Type': 'application/json'},
      );
      if (response.statusCode == 200 || response.statusCode == 201) {
        return Map<String, dynamic>.from(jsonDecode(response.body));
      } else {
        return Future.error("Error: ${response.statusCode}", StackTrace.fromString(response.body));
      }
    } on SocketException {
      return Future.error('No Internet connection');
    } catch (e) {
      return Future.error('Unexpected error: $e');
    }
  }

  static Future<LoginResult> loginCompany(String username, String password, String pinnumber) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    AuthRepo authRepo = AuthRepo(preferences);
    try {
      final response = await http.post(
        Uri.parse(loginUrl),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({
          "username": username.trim(),
          "password": password.trim(),
          "pinnumber": pinnumber.trim(),
        }),
      );

      print('Login response status: ${response.statusCode}');
      print('Login response body: ${response.body}');

      if (response.statusCode == 200) {
        String token = '';
        String? authHeader = response.headers['authorization'];
        if (authHeader != null && authHeader.startsWith('Bearer ')) {
          token = authHeader.substring(7);
        } else {
          final body = jsonDecode(response.body);
          token = body['token'] ?? '';
        }
        await authRepo.saveToken(token);
        return LoginResult.ok(token);
      }

      String serverMessage = '';
      try {
        serverMessage = jsonDecode(response.body)['message'] ?? '';
      } catch (_) {}

      switch (response.statusCode) {
        case 401:
          return LoginResult.fail(serverMessage.isNotEmpty ? serverMessage : 'Invalid Username, Password, or Pin Number.');
        case 403:
          return LoginResult.fail(serverMessage.isNotEmpty ? serverMessage : 'You do not have access for SeReports.');
        case 402:
          return LoginResult.fail(serverMessage.isNotEmpty ? serverMessage : 'Your SeReports Subscription has expired.');
        default:
          return LoginResult.fail(serverMessage.isNotEmpty ? serverMessage : 'Login failed.');
      }
    } on SocketException {
      return const LoginResult.fail('No internet connection.');
    } catch (e) {
      return LoginResult.fail('Login failed: $e');
    }
  }

  static Future<Map<String, dynamic>> getUserPermissions() async {
    return await Api.get(url: Api.userPermissions, parameter: {});
  }
}