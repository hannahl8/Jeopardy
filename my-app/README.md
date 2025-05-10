# React + TypeScript + Vite

### Installs in this project
1. `npm install react-router-dom ` - for routing
2. Run `npm install formik --save` to install formik 
3. Run `npm install yup --save` to install yup
4. Run `npm install axios` to install axios
5. Run `npm install --save-dev @types/axios` to install axios types 
6. Run `npm i --save @fortawesome/fontawesome-svg-core` to install fontawesome
7. Run `npm install --save @fortawesome/free-solid-svg-icons` to install fontawesome
8. Run `npm install --save @fortawesome/react-fontawesome` to install fontawesome
9. Run `npm install --save @fortawesome/free-brands-svg-icons` to install fontawesome

Add the following to index.html to import font-awesome icons from my account
```html
    <!-- Import font-awesome icons -->
<script src="https://kit.fontawesome.com/29ce78ff92.js" crossorigin="anonymous"></script>
```



This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type aware lint rules:

- Configure the top-level `parserOptions` property like this:

```js
export default tseslint.config({
  languageOptions: {
    // other options...
    parserOptions: {
      project: ['./tsconfig.node.json', './tsconfig.app.json'],
      tsconfigRootDir: import.meta.dirname,
    },
  },
})
```

- Replace `tseslint.configs.recommended` to `tseslint.configs.recommendedTypeChecked` or `tseslint.configs.strictTypeChecked`
- Optionally add `...tseslint.configs.stylisticTypeChecked`
- Install [eslint-plugin-react](https://github.com/jsx-eslint/eslint-plugin-react) and update the config:

```js
// eslint.config.js
import react from 'eslint-plugin-react'

export default tseslint.config({
  // Set the react version
  settings: { react: { version: '18.3' } },
  plugins: {
    // Add the react plugin
    react,
  },
  rules: {
    // other rules...
    // Enable its recommended rules
    ...react.configs.recommended.rules,
    ...react.configs['jsx-runtime'].rules,
  },
})
```
