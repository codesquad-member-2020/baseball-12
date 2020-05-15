import React, { useEffect, useRef } from 'react';
import drawFiled from './drawFiled';

const Filed = ({ width, height }) => {
  let ref = useRef();
  useEffect(() => {
    let canvas = ref.current;
    let context = canvas.getContext('2d');
    drawFiled(context);
  }, []);
  return <canvas ref={ref} width={width} height={height} />;
};

export default Filed;
