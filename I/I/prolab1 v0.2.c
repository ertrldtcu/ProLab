#include <stdio.h>
#include <math.h>
#include <allegro5/allegro.h>
#include <allegro5/allegro_primitives.h>
#include <allegro5/allegro_font.h>
#include <allegro5/allegro_ttf.h>

struct Point {
	float x,y;
};

struct Circle {
	struct Point center;
	float radius;
} circle = { { 0.0, 0.0 }, 0.0 };

const int displayW = 950, displayH = 950; // display width and height
int coordinateRange = 10; // means coordinate range will between -coordinateScale/2 to +coordinateScale/2
int pointCount = 0; // count of points
float scale; // distance between axis points (pixels)

void readPoints( struct Point a[] ); // read points from file
float dist( struct Point p1, struct Point p2 ); // distance between 2 points
float calculateIntersectionX( struct Point p1, float m1, struct Point p2, float m2 ); // intersection point of two lines with a known point and slope
int fact( int n ); // calculate the factorial
int comb( int n, int r ); // calculate the combination

void main() {

	// create points array and read points
	struct Point points[50];
	readPoints(points);

	// init allegro
	al_init();
	if (!al_init_primitives_addon() || !al_init_font_addon() || !al_init_ttf_addon()) {
		printf("Eklentiler baslatilamadi!");
		exit(1);
	}

	// calculate coordinate plane attributes
	for (int i = 0; i < pointCount; i++) {
		if (abs(points[i].x) * 2 + 10 > coordinateRange) coordinateRange = abs(points[i].x) * 2 + 10;
		if (abs(points[i].y) * 2 + 10 > coordinateRange) coordinateRange = abs(points[i].y) * 2 + 10;
	}
	scale = (float)displayW / coordinateRange;

	// create font
	ALLEGRO_FONT *font9 = al_load_font("font.ttf", 9, 0);
	ALLEGRO_FONT *font10 = al_load_font("font.ttf", 10, 0);
	ALLEGRO_FONT *font12 = al_load_font("font.ttf", 12, 0);

	// draw background
	ALLEGRO_DISPLAY *display = al_create_display(displayW, displayH);

	// set some colors
	const ALLEGRO_COLOR axesColor = al_map_rgb(125, 125, 125);
	const ALLEGRO_COLOR lineColor = al_map_rgb(35, 35, 35);

	// axis points
	for (int i = 0; i <= coordinateRange; i++) {
		int k = !(abs(i - coordinateRange / 2) % 5) + 1;
		// squares
		al_draw_line(i * scale, 0, i * scale, displayH, lineColor, 1);
		al_draw_line(0, i * scale, displayW, i * scale, lineColor, 1);

		// axes points
		al_draw_line(i * scale, displayH / 2 - k * 3 - 1, i * scale, displayH / 2 + k * 3, axesColor, k);
		al_draw_textf(font9, axesColor, i * scale, displayH / 2, 0, "%d", i - coordinateRange / 2);
		al_draw_line(displayW / 2 - k * 3 - 1, i * scale, displayW / 2 + k * 3, i * scale, axesColor, k);
		al_draw_textf(font9, axesColor, displayW / 2, i * scale, 0, "%d", coordinateRange / 2 - i);
	}

	// axes
	al_draw_line(0, displayH / 2, displayW, displayH / 2, axesColor, 1);
	al_draw_line(displayW / 2, 0, displayW / 2, displayH, axesColor, 1);

	// circle

	if (pointCount > 1) {
		// step 1: use the two furthest points as diameters
		int mostFar1, mostFar2;
		for (int i = 0; i < pointCount; i++) {
			for (int j = i + 1; j < pointCount; j++) {
				float distance = dist(points[i], points[j]);
				if (circle.radius < distance / 2.0) {
					mostFar1 = i;
					mostFar2 = j;
					circle.radius = distance / 2.0;
				}
			}
		}
		circle.center.x = (points[mostFar1].x + points[mostFar2].x) / 2.0;
		circle.center.y = (points[mostFar1].y + points[mostFar2].y) / 2.0;

		// step 2: detect points outside the circle (3rd farthest point)
		int mostFar3 = 0;
		while (mostFar3 != -1) {
			mostFar3 = -1;
			float mostFar3Distance = 0.0;
			for (int i = 0; i < pointCount; i++) {
				if (i != mostFar1 && i != mostFar2) {
					float dcp = dist(circle.center, points[i]);
					if (dcp > circle.radius + 0.00001 && dcp > mostFar3Distance) {
						mostFar3 = i;
						mostFar3Distance = dcp;
					}
				}
			}

			if (mostFar3 != -1) { // if there is the 3rd farthest point

				// calculate the distance of 3 points to each other and calculate the new radius with formula
				float a = dist(points[mostFar1], points[mostFar2]); // distance between 1 and 2
				float b = dist(points[mostFar1], points[mostFar3]); // distance between 1 and 3
				float c = dist(points[mostFar2], points[mostFar3]); // distance between 2 and 3
				circle.radius = (a*b*c) / sqrt((a + b + c) * (a + b - c) * (a + c - b) * (b + c - a)); // https://www.mathopenref.com/trianglecircumcircle.html

				// calculate the new center position of circle
				// x and y values ​​of the middle point, should not be the same as other points
				struct Point middlePoint = points[mostFar3];
				struct Point point1 = points[mostFar1];
				struct Point point2 = points[mostFar2];
				if (points[mostFar1].x != points[mostFar2].x && points[mostFar1].y != points[mostFar2].y && points[mostFar1].x != points[mostFar3].x && points[mostFar1].y != points[mostFar3].y) {
					middlePoint = points[mostFar1];
					point1 = points[mostFar2];
					point2 = points[mostFar3];
				}
				else if (points[mostFar2].x != points[mostFar1].x && points[mostFar2].y != points[mostFar1].y && points[mostFar2].x != points[mostFar3].x && points[mostFar2].y != points[mostFar3].y) {
					middlePoint = points[mostFar2];
					point1 = points[mostFar1];
					point2 = points[mostFar3];
				}

				struct Point centerOfSide1 = { (middlePoint.x + point1.x) / 2.0, (middlePoint.y + point1.y) / 2.0 };
				float slope1 = -1.0 / ((point1.y - middlePoint.y) / (point1.x - middlePoint.x));
				struct Point centerOfSide2 = { (middlePoint.x + point2.x) / 2.0, (middlePoint.y + point2.y) / 2.0 };
				float slope2 = -1.0 / ((point2.y - middlePoint.y) / (point2.x - middlePoint.x));
				circle.center.x = calculateIntersectionX(centerOfSide1, slope1, centerOfSide2, slope2);
				circle.center.y = slope1 * (circle.center.x - centerOfSide1.x) + centerOfSide1.y; // calculate the y position using the x value ( y = m1*(x-x1) + y1 )
				if (dist(points[mostFar1], points[mostFar3]) > dist(points[mostFar2], points[mostFar3])) {
					int temp = mostFar2;
					mostFar2 = mostFar3;
					mostFar3 = temp;
				}
				else {
					int temp = mostFar1;
					mostFar1 = mostFar3;
					mostFar3 = temp;
				}
			}

		}

		// print & draw circle information
		printf("centerX: %f\ncenterY: %f\n radius: %f\n", circle.center.x, circle.center.y, circle.radius);
		al_draw_textf(font12, al_map_rgb(255, 255, 255), 0, 0, 0, "centerX: %f", circle.center.x);
		al_draw_textf(font12, al_map_rgb(255, 255, 255), 0, scale, 0, "centerY: %f", circle.center.y);
		al_draw_textf(font12, al_map_rgb(255, 255, 255), 0, 2 * scale, 0, "radius: %f", circle.radius);

		// draw radius line & text
		al_draw_line(displayW / 2.0 + circle.center.x * scale, displayH / 2.0 - circle.center.y * scale, displayW / 2.0 + (circle.center.x + circle.radius) * scale, displayH / 2.0 - circle.center.y * scale, al_map_rgb(214, 157, 98), 2);
		al_draw_textf(font12, al_map_rgb(214, 157, 98), displayW / 2.0 + (circle.center.x + circle.radius) * scale / 2.0, displayH / 2.0 - (circle.center.y + 1) *  scale, ALLEGRO_ALIGN_CENTER, "r = %f", circle.radius);

		// draw center point & text
		al_draw_filled_circle(displayW / 2 + circle.center.x * scale, displayH / 2 - circle.center.y * scale, 3, al_map_rgb(214, 157, 98));
		al_draw_textf(font12, al_map_rgb(214, 157, 98), displayW / 2.0 + circle.center.x * scale, displayH / 2.0 - circle.center.y * scale, 0, "(%.2f,%.2f)", circle.center.x, circle.center.y);

		// draw circle
		al_draw_circle(displayW / 2 + circle.center.x * scale, displayH / 2 - circle.center.y * scale, circle.radius * scale, al_map_rgb(255, 255, 255), 2);
	}

	// spline
	for (float t = 0.0;t <= 1.0;t += 0.0001) { // https://en.wikipedia.org/wiki/De_Casteljau%27s_algorithm
		float x = 0.0, y = 0.0;
		for (int i = 0;i < pointCount;i++) { // n = pointCount - 1 //
			x += comb(pointCount-1, i) * pow(1 - t, pointCount - 1 - i) * pow(t, i) * points[i].x;
			y += comb(pointCount-1, i) * pow(1 - t, pointCount - 1 - i) * pow(t, i) * points[i].y;
		}
		al_draw_filled_circle(displayW / 2 + x * scale, displayH / 2 - y * scale, 1.25, al_map_rgb(0, 255, 0));
	}

	// points
	for (int i = 0; i < pointCount; i++) {
		al_draw_filled_circle(displayW / 2 + points[i].x * scale, displayH / 2 - points[i].y * scale, 4, axesColor);
		al_draw_filled_circle(displayW / 2 + points[i].x * scale, displayH / 2 - points[i].y * scale, 3, al_map_rgb(0, 0, 155));
		al_draw_textf(font10, axesColor, displayW / 2 + points[i].x * scale + 5, displayH / 2 - points[i].y * scale, 0, "%d(%.0f,%.0f)", i + 1, points[i].x, points[i].y);
	}

	// wait after draw
	al_flip_display();
	system("pause");
}

void readPoints( struct Point a[] ) {

	FILE *file = fopen("points.csv", "r");

	if (!file) {
		printf("Dosya acilamadi. Program durduruldu.");
		exit(1);
	}

	while (!feof(file)) {
		int succes = fscanf(file, "%f,%f", &a[pointCount].x, &a[pointCount].y);
		if (succes != 2) {
			printf("Dosya okumasi basarisiz. Toplam okunan nokta sayisi: %d\n", pointCount);
			if (pointCount == 0) {
				printf("Program durduruldu!\n");
				exit(1);
			}
			printf("Mevcut noktalarla calismak icin E veya e , programi kapatmak icin H veya h yaziniz: ");
			char result;
			scanf("%c", &result);
			if (result == 'E' || result == 'e') {
				fclose(file);
				break;
			}
			else {
				printf("Program durduruldu!");
				exit(1);
			}
		}
		pointCount++;
	}
	fclose(file);
}

float dist( struct Point p1, struct Point p2 ) {
	return sqrt( (p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y) );
}

float calculateIntersectionX( struct Point p1, float m1, struct Point p2, float m2 ) {
	/*
		// the formula for line whose point and slope are known
		y - y1 = m1*(x - x1) --> y = m1x - m1x1 + y1
		y - y2 = m2*(x - x2) --> y = m2x - m2x2 + y2

		// x position of the intersection point
		m1x - m1x1 + y1 = m2x - m2x2 + y2
		m1x - m2x = m1x1 - m2x2 - y1 + y2
		x*(m1 - m2) = m1x1 - m2x2 - y1 + y2
		x = (m1x1 - m2x2 - y1 + y2) / (m1 - m2)

	*/
	return ( p1.x*m1 - p2.x*m2 - p1.y + p2.y ) / (m1 - m2);
}

int fact( int n ) {
	if (n <= 1) return 1;
	else return n * fact(n - 1);
}

int comb( int n, int r ) {
	return fact(n) / ( fact(n - r) * fact(r) );
}
