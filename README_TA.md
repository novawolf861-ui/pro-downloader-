# VideoVoidPro Android starter

## இதில் இப்போது வேலை செய்வது
- Dark theme, mobile-friendly native Android UI
- URL validation மற்றும் சில பொதுவான platform domains-ஐ அடையாளம் காணுதல்
- நேரடி `.mp4`, `.webm`, `.mov`, `.m4v`, `.3gp`, `.mkv` file URL-களை Android DownloadManager மூலம் Downloads folder-க்கு அனுப்புதல்
- Android notification மூலம் download status

## இது இன்னும் செய்யாதவை
YouTube / Instagram / Facebook / TikTok போன்ற website page URL-களிலிருந்து வீடியோ stream-ஐ extract செய்யாது. Quality selector-ம் direct file-ஐ மாற்றாது. Platform-களுக்கான அதிகாரப்பூர்வமாக அனுமதிக்கப்பட்ட API அல்லது தனித்தனி backend integration தேவை.

## APK உருவாக்குவது
1. ZIP-ஐ extract செய்யவும்.
2. Android Studio → **Open** → `VideoVoidProAndroid` folder தேர்வு செய்யவும்.
3. Gradle sync முடியும் வரை காத்திருக்கவும்.
4. **Build → Build APK(s)** தேர்வு செய்யவும்.
5. APK பொதுவாக `app/build/outputs/apk/debug/app-debug.apk`-ல் கிடைக்கும்.

JDK 17 மற்றும் Android SDK Platform 35 நிறுவப்பட்டிருக்க வேண்டும். Gradle wrapper இந்த ZIP-ல் சேர்க்கப்படவில்லை; Android Studio-வின் Gradle setup பயன்படுத்தவும்.

## பொறுப்பான பயன்பாடு
உங்களுடைய வீடியோ அல்லது download செய்ய அனுமதி உள்ள உள்ளடக்கத்தை மட்டும் சேமிக்கவும். DRM, private access control, அல்லது creator restrictions-ஐ bypass செய்யாது.
