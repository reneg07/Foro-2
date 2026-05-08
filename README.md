# 💸 Control de Gastos

Aplicación Android para registrar, consultar y resumir gastos personales con **Firebase Authentication** y **Firestore**.

## ✨ Funcionalidades

- 🔐 Inicio de sesión con correo y Google
- 📝 Registro de usuarios
- ➕ Registro de nuevos gastos
- ☁️ Guardado en Firestore por usuario autenticado
- 📚 Historial de gastos
- 📊 Total mensual en pantalla principal
- 🔎 Filtro por categoría

## 🧰 Tecnologías

- Kotlin
- Android SDK
- Firebase Authentication
- Firebase Firestore
- Material Design
- ConstraintLayout
- RecyclerView

## 📱 Flujo de la app

1. El usuario inicia sesión.
2. Entra al Home.
3. Desde ahí puede:
   - crear un nuevo gasto
   - ver el historial
   - consultar el total mensual
4. Los datos se guardan en Firestore bajo cada usuario.

## 🗂 Estructura principal

- `MainActivity.kt` - Login
- `RegisterActivity.kt` - Registro
- `HomeActivity.kt` - Pantalla principal
- `GastosActivity.kt` - Formulario de gasto
- `HistorialActivity.kt` - Lista de gastos
- `Gasto.kt` - Modelo de datos
- `GastoAdapter.kt` - Adaptador de RecyclerView

## 📄 Documento PDF

<!-- Adjuntar aquí el archivo PDF del informe -->

## 🎥 Video de YouTube

<!-- Adjuntar aquí el enlace del video de presentación -->

## 🚀 Ejecución

1. Abrir el proyecto en Android Studio o IntelliJ IDEA Ultimate.
2. Agregar `google-services.json` dentro de la carpeta `app/`.
3. Sincronizar Gradle.
4. Ejecutar en un dispositivo físico o emulador.

## 👥 Equipo

- Integrante 1
- Integrante 2
- Integrante 3
- Integrante 4

## 📌 Notas

- El archivo `google-services.json` no se sube al repositorio.
- Firestore requiere conexión a internet para guardar y consultar datos.
