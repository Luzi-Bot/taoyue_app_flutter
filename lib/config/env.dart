/// 全局配置（dev/staging/prod 编译时由 --dart-define 注入）
/// dev     默认值，无需传参
/// staging flutter build apk --flavor staging --dart-define=APP_ENV=staging --dart-define=API_BASE_URL=https://staging-api.taoyue.com
/// prod    flutter build apk --flavor prod    --dart-define=APP_ENV=prod    --dart-define=API_BASE_URL=https://api.taoyue.com
class AppConfig {
  AppConfig._();

  static const String env =
      String.fromEnvironment('APP_ENV', defaultValue: 'dev');
  static const String baseUrl = String.fromEnvironment(
    'API_BASE_URL',
    defaultValue: 'http://127.0.0.1:8000',
  );
  static const String commitHash =
      String.fromEnvironment('COMMIT_HASH', defaultValue: 'unknown');
  static const String buildNumber =
      String.fromEnvironment('BUILD_NUMBER', defaultValue: '0');
  static bool get isProd => env == 'prod';
  static const int pageSize = 10;
  static const int connectTimeout = 10;
  static const int receiveTimeout = 15;
  static const String tokenKey = 'taoyue_token';
  static const String userKey = 'taoyue_user';
}
