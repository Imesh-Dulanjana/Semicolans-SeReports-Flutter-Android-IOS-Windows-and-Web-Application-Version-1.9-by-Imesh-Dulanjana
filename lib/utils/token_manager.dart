import 'package:shared_preferences/shared_preferences.dart';

class TokenManager {
  final SharedPreferences _preferences;
  final String _tokenKey = 'userToken';
  final String _expiryKey = 'userTokenExpiry';

  TokenManager(this._preferences);

  Future<void> saveToken(String token, {int expiryDays = 90}) async {
    await _preferences.setString(_tokenKey, token);

    // Calculate expiry date (current time + 90 days)
    final expiry =
        DateTime.now().add(Duration(days: expiryDays)).millisecondsSinceEpoch;
    await _preferences.setInt(_expiryKey, expiry);
  }

  Future<String?> getToken() async {
    final token = _preferences.getString(_tokenKey);
    final expiry = _preferences.getInt(_expiryKey);

    if (token == null || expiry == null) {
      return null;
    }

    // Check if token is expired
    if (DateTime.now().millisecondsSinceEpoch > expiry) {
      // Token expired, clear it and return null
      await clearToken();
      return null;
    }

    return token;
  }

  Future<bool> hasValidToken() async {
    return await getToken() != null;
  }

  Future<void> clearToken() async {
    await _preferences.remove(_tokenKey);
    await _preferences.remove(_expiryKey);
  }
}
