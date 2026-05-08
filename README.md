<div align="center">

# 💸 Control de Gastos

**Aplicación Android para el registro y control de gastos personales**

*Firebase Authentication · Firestore · Material Design*

---

</div>

## ✨ Funcionalidades

| Icono | Funcionalidad | Descripción |
|:-----:|:-------------|:------------|
| 🔐 | Autenticación | Inicio de sesión con correo electrónico y Google |
| 📝 | Registro | Creación de cuentas de usuario |
| ➕ | Nuevo Gasto | Registro de gastos con nombre, monto, categoría y fecha |
| ☁️ | Cloud Firestore | Almacenamiento en la nube asociado al usuario autenticado |
| 📚 | Historial | Visualización de todos los gastos registrados |
| 📊 | Total Mensual | Cálculo automático del total de gastos del mes actual |
| 🔎 | Filtro por Categoría | Filtrado de gastos por categoría en el historial |

---

## 🧰 Stack Tecnológico

| Categoría | Tecnología |
|:---------:|:----------:|
| 💻 Lenguaje | **Kotlin** |
| 📱 Plataforma | **Android SDK 34** |
| 🔥 Backend | **Firebase Authentication** |
| ☁️ Base de Datos | **Firebase Firestore** |
| 🎨 Diseño | **Material Design 3** |
| 📐 Layouts | **ConstraintLayout · LinearLayout** |
| 📋 Listas | **RecyclerView** |
| 🔧 Build | **Gradle 8.0 · AGP 8.1.1** |

---

## 📱 Flujo de la Aplicación

```
 🚀 App Abierta
     │
     ▼
 📱 MainActivity ──── Login con correo / Google
     │                        │
     │                   RegisterActivity
     │                   (Registro de usuarios)
     │
     ▼ (Login exitoso)
 🏠 HomeActivity
     │
     ├── ➕ Nuevo Gasto ──→ GastosActivity ──→ ☁️ Firestore
     │
     ├── 📚 Historial ────→ HistorialActivity ← ☁️ Firestore
     │
     ├── 📊 Total Mensual (cálculo automático en Home)
     │
     └── 🚪 Cerrar sesión ──→ MainActivity
```

---

## 🗂 Estructura del Proyecto

```
app/src/main/
├── java/com/example/controldegastos/
│   ├── 📄 MainActivity.kt ............. Pantalla de Login
│   ├── 📄 RegisterActivity.kt ......... Pantalla de Registro
│   ├── 🏠 HomeActivity.kt ............. Pantalla Principal
│   ├── ➕ GastosActivity.kt ........... Formulario de Nuevo Gasto
│   ├── 📚 HistorialActivity.kt ........ Historial de Gastos
│   ├── 🗃 Gasto.kt .................... Modelo de Datos
│   └── 🔌 GastoAdapter.kt ............ Adaptador RecyclerView
│
└── res/
    ├── layout/
    │   ├── activity_main.xml .......... Login
    │   ├── activity_register.xml ...... Registro
    │   ├── activity_home.xml .......... Home (total + botones)
    │   ├── activity_gastos.xml ........ Formulario de gasto
    │   ├── activity_historial.xml ..... Historial con filtros
    │   └── item_gasto.xml ............. Fila de la lista
    │
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes.xml
```

---

## ☁️ Estructura en Firestore

```
Firestore Database
└── users/ (colección)
    └── {uid}/ (documento por usuario)
        └── gastos/ (subcolección)
            └── {autoId}/
                ├── nombre: "Supermercado"
                ├── monto: 150.50
                ├── categoria: "Alimentación"
                ├── fecha: "2026-05-03"
                └── fechaTimestamp: Timestamp
```

---

## 🚀 Cómo Ejecutar

1. 📥 Clonar el repositorio
2. 📁 Colocar `google-services.json` en la carpeta `app/`
3. 🔄 Sincronizar Gradle
4. 📱 Conectar un dispositivo físico o emulador
5. ▶️ Ejecutar el proyecto

> 📁 El archivo `google-services.json` debe colocarse en la carpeta `app/` antes de ejecutar el proyecto

---

## 👥 Equipo de Desarrollo

| Nombre | Carnet |
|:------:|:------:|
| Ronald Alexander Martínez Gutiérrez | MG223061 |
| Katherine Paola Pineda Rodríguez | PR232427 |
| René Francisco Guevara Alfaro | GA202826 |
| Karina Lisbeth Angel Quezada | AQ161844 |

---

## 📄 Documento PDF

> 📎 **[Adjuntar aquí el archivo PDF del informe]**

---

## 🎥 Video de Presentación

> 🔗 **[Adjuntar aquí el enlace del video de YouTube]**

---

## 📌 Notas

- 🔒 `google-services.json` debe colocarse en `app/` (no se incluye en el repositorio)
- 🌐 Firestore requiere conexión a internet para guardar y consultar datos
- 📱 La app es compatible con Android 7.0 (API 24) en adelante

---

<div align="center">

*Proyecto desarrollado para el Foro #2 — Control de Gastos*

</div>
