import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GrobalStyle = createGlobalStyle`
  ${reset}
  * {
    box-sizing: border-box;
  }
  body{
    @font-face {
      font-family: 'Binggrae-Bold';
      src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/Binggrae-Bold.woff') format('woff');
      font-weight: normal;
      font-style: normal;
    }
    background-color: #ffffff;
    font-family: 'Binggrae-Bold', -apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif;
  }
  a {
    color: inherit;
    text-decoration: none;
  }
  input, button {
    background-color: transparent;
    border: none;
    outline: none;
    font: inherit;
  }
  ol, ul, li {
    list-style: none;
  }
  button {
    cursor: pointer;
  }
  img {
    display: block;
    width: 100%;
    height: auto;
  }
`;

export default GrobalStyle;
