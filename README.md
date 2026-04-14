# DataForm Android Library

A flexible and extensible Android library for rendering dynamic forms with multiple question types, validation, and progress tracking.

## Features

- **Multiple Question Types**
  - Text input (textual)
  - Single choice (dropdown/spinner)
  - Multiple choice (checkboxes)
  - Radio button options
  - Image capture
  - Percentage input
  - Informative text sections

- **Built-in Capabilities**
  - Form validation
  - Progress tracking with add/remove functionality
  - Image capture from camera or gallery
  - Input masking (CPF, phone, CEP, etc.)
  - State preservation via Parcelable
  - Multi-language support (Portuguese & English)

## Use Cases

- Survey applications
- Data collection forms
- questionnaires
- Customer feedback forms
- Registration forms
- Any dynamic data entry scenario

## Quick Start

Add to your `build.gradle`:

```groovy
implementation 'com.github.pedrofsn:data-form:LATEST-VERSION'
```

## Architecture

The library follows a component-based architecture:

- **Model Layer**: `Form`, `Question`, `Answer`, `FormSettings`
- **UI Layer**: Individual UI components for each question type
- **Domain Layer**: Adapters and handlers for rendering

## Requirements

- Min SDK: 16
- Target SDK: 30
- Kotlin 1.6.0+
- AndroidX compatible