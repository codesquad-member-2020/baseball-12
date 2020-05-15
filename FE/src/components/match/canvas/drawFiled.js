const drawFiled = (ctx) => {
  ctx.beginPath();
  ctx.moveTo(50, 200);
  ctx.lineTo(200, 50);
  ctx.lineTo(350, 200);
  ctx.lineTo(200, 350);
  ctx.lineTo(50, 200);
  ctx.strokeStyle = '#fff';
  ctx.stroke();
  ctx.closePath();

  ctx.beginPath();
  ctx.moveTo(320, 200);
  ctx.lineTo(350, 170);
  ctx.lineTo(380, 200);
  ctx.lineTo(350, 230);

  ctx.moveTo(170, 50);
  ctx.lineTo(200, 20);
  ctx.lineTo(230, 50);
  ctx.lineTo(200, 80);

  ctx.moveTo(20, 200);
  ctx.lineTo(50, 170);
  ctx.lineTo(80, 200);
  ctx.lineTo(50, 230);

  ctx.moveTo(170, 350);
  ctx.lineTo(200, 320);
  ctx.lineTo(230, 350);
  ctx.lineTo(230, 380);
  ctx.lineTo(170, 380);

  ctx.closePath();
  ctx.fillStyle = '#fff';
  ctx.fill();
};
export default drawFiled;
