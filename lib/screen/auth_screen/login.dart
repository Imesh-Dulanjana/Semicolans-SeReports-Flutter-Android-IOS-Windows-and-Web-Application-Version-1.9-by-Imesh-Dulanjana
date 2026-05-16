import 'package:flutter/material.dart';
import 'package:sereports/constants.dart';
import 'package:sereports/repository/auth_repo.dart';
import 'package:sereports/screen/dashboard/dashbaord.dart';
import 'package:sereports/utils/user_session.dart';
import 'package:sereports/widget/snackbar.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _pinnumberController = TextEditingController();

  bool _isLoading = false;
  bool _showPassword = false;
  bool _showPinnumber = false;
  String? _emailError;
  String? _passwordError;
  String? _pinnumberError;

  @override
  void initState() {
    super.initState();
    _checkExistingLogin();
  }

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    _pinnumberController.dispose();
    super.dispose();
  }

  bool _validateFields() {
    setState(() {
      _emailError = _emailController.text.trim().isEmpty ? 'Username is required' : null;
      _passwordError = _passwordController.text.isEmpty ? 'Password is required' : null;
      _pinnumberError = _pinnumberController.text.trim().isEmpty ? 'Pin number is required' : null;
    });
    return _emailError == null && _passwordError == null && _pinnumberError == null;
  }

  Future<void> _login() async {
    if (!_validateFields()) return;
    setState(() => _isLoading = true);
    try {
      final SharedPreferences preferences = await SharedPreferences.getInstance();
      final result = await AuthRepo(preferences).login(
        _emailController.text.trim(),
        _passwordController.text,
        _pinnumberController.text.trim(),
      );
      if (result.success) {
        showSuccessSnackBar(context, 'Login successful!');
        _navigateToDashboard();
      } else {
        showErrorSnackBar(context, result.errorMessage ?? 'Login failed.');
      }
    } catch (e) {
      showErrorSnackBar(context, 'Login failed: $e');
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  Future<void> _checkExistingLogin() async {
    final prefs = await SharedPreferences.getInstance();
    if (await AuthRepo(prefs).isLoggedIn()) {
      await UserSession.instance.loadFromPrefs();
      if (mounted) _navigateToDashboard();
    }
  }

  void _navigateToDashboard() {
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(builder: (context) => const DashbaordScreen()),
    );
  }

  Widget _buildTextField({
    required TextEditingController controller,
    required String hint,
    required IconData icon,
    bool obscure = false,
    TextInputType keyboardType = TextInputType.text,
    String? errorText,
    Widget? suffixIcon,
  }) {
    final bool hasError = errorText != null;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          height: 52,
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(radiusValue),
            border: Border.all(
              color: hasError ? Colors.red.shade400 : const Color(0xFFDDE1E7),
              width: 1.4,
            ),
            boxShadow: [
              BoxShadow(
                color: Colors.black.withOpacity(0.04),
                blurRadius: 6,
                offset: const Offset(0, 2),
              ),
            ],
          ),
          child: TextField(
            controller: controller,
            obscureText: obscure,
            keyboardType: keyboardType,
            style: const TextStyle(fontSize: 14, color: Colors.black87),
            decoration: InputDecoration(
              hintText: hint,
              hintStyle: TextStyle(color: grayColorForHintText, fontSize: 14),
              prefixIcon: Icon(
                icon,
                size: 20,
                color: hasError ? Colors.red.shade400 : const Color(0xFF9AA0A6),
              ),
              suffixIcon: suffixIcon,
              border: InputBorder.none,
              contentPadding: const EdgeInsets.symmetric(vertical: 15),
            ),
          ),
        ),
        if (hasError) ...[
          const SizedBox(height: 5),
          Row(
            children: [
              Icon(Icons.error_outline_rounded, size: 13, color: Colors.red.shade400),
              const SizedBox(width: 4),
              Text(
                errorText,
                style: TextStyle(fontSize: 11.5, color: Colors.red.shade400, fontWeight: FontWeight.w500),
              ),
            ],
          ),
        ],
      ],
    );
  }

  Widget _buildEyeToggle({required bool show, required VoidCallback onPressed}) {
    return IconButton(
      icon: Icon(show ? Icons.visibility_off : Icons.visibility, size: 20),
      onPressed: onPressed,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey.shade100,
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset('assets/logo.png', height: 80),
              const SizedBox(height: 20),
              Text(
                'SeReports',
                style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold, color: Colors.blue.shade800),
              ),
              const SizedBox(height: 40),
              _buildTextField(
                controller: _emailController,
                hint: 'Username',
                icon: Icons.person_outline,
                errorText: _emailError,
              ),
              const SizedBox(height: 16),
              _buildTextField(
                controller: _passwordController,
                hint: 'Password',
                icon: Icons.lock_outline,
                obscure: !_showPassword,
                errorText: _passwordError,
                suffixIcon: _buildEyeToggle(
                  show: _showPassword,
                  onPressed: () => setState(() => _showPassword = !_showPassword),
                ),
              ),
              const SizedBox(height: 16),
              _buildTextField(
                controller: _pinnumberController,
                hint: 'Pin Number',
                icon: Icons.pin,
                obscure: !_showPinnumber,
                keyboardType: TextInputType.number,
                errorText: _pinnumberError,
                suffixIcon: _buildEyeToggle(
                  show: _showPinnumber,
                  onPressed: () => setState(() => _showPinnumber = !_showPinnumber),
                ),
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton(
                  onPressed: _isLoading ? null : _login,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue.shade700,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(radiusValue),
                    ),
                  ),
                  child: _isLoading
                      ? const SizedBox(
                          height: 24,
                          width: 24,
                          child: CircularProgressIndicator(strokeWidth: 2, color: Colors.white),
                        )
                      : const Text('Login', style: TextStyle(fontSize: 16)),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}