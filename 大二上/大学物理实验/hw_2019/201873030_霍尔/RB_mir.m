x=[0.0, 5.0 ,10.0 ,15.0 ,20.0, 25.0,30.0];
y=[0.0063 ,0.0063 ,0.0062 ,0.0060 ,0.0055 ,0.0048 ,0.0040 ];
yn=fliplr(y);
y=[yn,y];
xn=fliplr(x).*(-1);
x=[xn,x];
plot(x,y);
xlabel('R/(mm)');
ylabel('B/(T)');
title('亥姆霍兹线圈径向磁场分布（镜像处理后）');
grid on;
set(gca,'Xtick',[-30.0:5.0:30.0]);
set(gca,'Ytick',[0:0.00025:0.007]);