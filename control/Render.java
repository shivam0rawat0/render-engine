package control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import lib.Cube;
import lib.Edge;
import lib.Point;
import lib.Transform;

public class Render extends JPanel {
    public int W, H, wd, hd;
    public int camera[] = { 0, 0, -50};
    public int plane[] = { 0, 0, 0};
    public int axis = 0;
    public double gAxis[][] = {
        {1,0,0},
        {0,1,0},
        {0,0,1},
    };

    public Color color[];
    public Cube cube[];
    public int count = -1;

    public Render(int W, int H, int size) {
        setBackground(Color.BLACK);
        this.W = W;
        this.H = H;
        wd = W / 2;
        hd = H / 2;
        color = new Color[size];
        cube = new Cube[size];
    }

    public void add(double x, double y, double w, double h, double z1, double z2, Color color) {
        this.color[++count] = color;
        Point nbl = new Point(x, y, z1);
        Point nbr = new Point(x + w, y, z1);
        Point ntl = new Point(x, y + h, z1);
        Point ntr = new Point(x + w, y + h, z1);
        Point fbl = new Point(x, y, z2);
        Point fbr = new Point(x + w, y, z2);
        Point ftl = new Point(x, y + h, z2);
        Point ftr = new Point(x + w, y + h, z2);
        cube[count] = new Cube();
        cube[count].rangle = new int[] {1, 1, 1};
        cube[count].setEdge(
                new Edge[] {
                        new Edge(ntl, ntr),
                        new Edge(ntl, nbl),
                        new Edge(nbr, nbl),
                        new Edge(nbr, ntr),
                        new Edge(ftl, ftr),
                        new Edge(ftl, fbl),
                        new Edge(fbr, fbl),
                        new Edge(fbr, ftr),
                        new Edge(nbl, fbl),
                        new Edge(nbr, fbr),
                        new Edge(ntl, ftl),
                        new Edge(ntr, ftr)
                });
        cube[count].rplane = new double[][] {
                // XY palne
                { (nbl.x + ntr.x) / 2, (nbl.y + ntr.y) / 2, nbl.z },
                // YZ plane
                { nbr.x, (nbr.y + ftr.y) / 2, (nbr.z + ftr.z) / 2 },
                // XZ Plane
                { (ntl.x + ftr.x) / 2, ntl.y, (ntl.z + ftr.z) / 2 }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double edges[][] = new double[8][3];
        double Tedges[][] = new double[12][6];
        for (int i = 0; i <= count; ++i) {
            int edgeCount = 0;
            for (Edge e : cube[i].e) {
                ++edgeCount;
                double x1 = (e.p[0].x * (e.p[0].z - plane[2])) / (plane[2] - camera[2]) + wd;
                double y1 = hd - (e.p[0].y * (e.p[0].z - plane[2])) / (plane[2] - camera[2]);
                double x2 = (e.p[1].x * (e.p[1].z - plane[2])) / (plane[2] - camera[2]) + wd;
                double y2 = hd - (e.p[1].y * (e.p[1].z - plane[2])) / (plane[2] - camera[2]);
                double z1 = (e.p[0].z * (e.p[1].z - plane[2])) / (plane[2] - camera[2]) + wd;
                double z2 = hd - (e.p[1].z * (e.p[1].z - plane[2])) / (plane[2] - camera[2]);
                Tedges[edgeCount - 1][0] = x1;
                Tedges[edgeCount - 1][1] = y1;
                Tedges[edgeCount - 1][2] = e.p[0].z;
                Tedges[edgeCount - 1][3] = x2;
                Tedges[edgeCount - 1][4] = y2;
                Tedges[edgeCount - 1][5] = e.p[1].z;
                if (edgeCount == 9) {
                    edges[0][0] = x1;
                    edges[0][1] = y1;
                    edges[0][2] = e.p[0].z;
                    edges[1][0] = x2;
                    edges[1][1] = y2;
                    edges[1][2] = e.p[1].z;
                } else if (edgeCount == 10) {
                    edges[2][0] = x1;
                    edges[2][1] = y1;
                    edges[2][2] = e.p[0].z;
                    edges[3][0] = x2;
                    edges[3][1] = y2;
                    edges[3][2] = e.p[1].z;
                } else if (edgeCount == 11) {
                    edges[4][0] = x1;
                    edges[4][1] = y1;
                    edges[4][2] = e.p[0].z;
                    edges[5][0] = x2;
                    edges[5][1] = y2;
                    edges[5][2] = e.p[1].z;
                } else if (edgeCount == 12) {
                    edges[6][0] = x1;
                    edges[6][1] = y1;
                    edges[6][2] = e.p[0].z;
                    edges[7][0] = x2;
                    edges[7][1] = y2;
                    edges[7][2] = e.p[1].z;
                }
            }
            double sides[][][] = new double[][][] {
                    {
                            { edges[1][0], edges[2][0], edges[6][0], edges[4][0] },
                            { edges[1][1], edges[2][1], edges[6][1], edges[4][1] },
                            { edges[1][2], edges[2][2], edges[6][2], edges[4][2] }
                    },
                    {
                            { edges[4][0], edges[6][0], edges[7][0], edges[5][0] },
                            { edges[4][1], edges[6][1], edges[7][1], edges[5][1] },
                            { edges[4][2], edges[6][2], edges[7][2], edges[5][2] }
                    },
                    {
                            { edges[0][0], edges[4][0], edges[5][0], edges[1][0] },
                            { edges[0][1], edges[4][1], edges[5][1], edges[1][1] },
                            { edges[0][2], edges[4][2], edges[5][2], edges[1][2] }
                    },
                    {
                            { edges[2][0], edges[6][0], edges[7][0], edges[3][0] },
                            { edges[2][1], edges[6][1], edges[7][1], edges[3][1] },
                            { edges[2][2], edges[6][2], edges[7][2], edges[3][2] }
                    },
                    {
                            { edges[0][0], edges[2][0], edges[3][0], edges[1][0] },
                            { edges[0][1], edges[2][1], edges[3][1], edges[1][1] },
                            { edges[0][2], edges[2][2], edges[3][2], edges[1][2] }
                    },
                    {
                            { edges[0][0], edges[2][0], edges[6][0], edges[4][0] },
                            { edges[0][1], edges[2][1], edges[6][1], edges[4][1] },
                            { edges[0][2], edges[2][2], edges[6][2], edges[4][2] }
                    },
            };
            g.setColor(color[i]);
            for (double face[][] : sides) {
                if (face[2][0] > plane[2] && face[2][1] > camera[2]) {
                    g.fillPolygon(dToIArr(face[0]),dToIArr(face[1]), 4);
                }
            }
            g.setColor(Color.BLACK);
            for (int j = 0; j < 12; ++j) {
                if (Tedges[j][2] > plane[2] && Tedges[j][5] > camera[2]) {
                    g.drawLine((int)Tedges[j][0],(int)Tedges[j][1],(int)Tedges[j][3],(int)Tedges[j][4]);
                }
            }
        }
    }

    public void redraw(int move) {
        switch (move) {
            case KeyEvent.VK_W:
                camera[2] -= 4;
                plane[2] -= 4;
                break;
            case KeyEvent.VK_S:
                camera[2] += 4;
                plane[2] += 4;
                break;
            case KeyEvent.VK_A:
                for (int i = 0; i <= count; ++i) {
                    for (Edge e : cube[i].e) {
                        ++e.p[0].x;
                        ++e.p[1].x;
                    }
                }
                break;
            case KeyEvent.VK_D:
                for (int i = 0; i <= count; ++i) {
                    for (Edge e : cube[i].e) {
                        --e.p[0].x;
                        --e.p[1].x;
                    }
                }
                break;
            case KeyEvent.VK_V:
                for (int i = 0; i <= count; ++i) {
                    for (Edge e : cube[i].e) {
                        ++e.p[0].y;
                        ++e.p[1].y;
                    }
                }
                break;
            case KeyEvent.VK_R:
                for (int i = 0; i <= count; ++i) {
                    for (Edge e : cube[i].e) {
                        --e.p[0].y;
                        --e.p[1].y;
                    }
                }
                break;
            case KeyEvent.VK_Q:
                for (int i = 0; i <= count; ++i) {
                    for (int j = 8; j < 12; ++j) {
                        for (int k = 0; k < 2; ++k) {
                            double[] rotAxis = gAxis[axis]; // Rotate around the z-axis
                            double angle = Math.toRadians(cube[i].rangle[axis]); // 90 degrees
                            Transform q = Transform.fromAxisAngle(rotAxis, angle);
                            // Define the vector to rotate
                            double[] v = {cube[i].e[j].p[k].x, cube[i].e[j].p[k].y, cube[i].e[j].p[k].z}; // Vector to rotate
                            // Rotate the vector
                            double[] rotV = q.rotateVector(v);
                            cube[i].e[j].p[k].x = rotV[0];
                            cube[i].e[j].p[k].y = rotV[1];
                            cube[i].e[j].p[k].z = rotV[2];
                        }
                    }
                }
                break;
            case KeyEvent.VK_E:
                for (int i = 0; i <= count; ++i) {
                    for (int j = 8; j < 12; ++j) {
                        for (int k = 0; k < 2; ++k) {
                            double[] rotAxis = gAxis[axis]; // Rotate around the z-axis
                            double angle = Math.toRadians(-cube[i].rangle[axis]); // 90 degrees
                            Transform q = Transform.fromAxisAngle(rotAxis, angle);
                            // Define the vector to rotate
                            double[] v = {cube[i].e[j].p[k].x, cube[i].e[j].p[k].y, cube[i].e[j].p[k].z}; // Vector to rotate
                            // Rotate the vector
                            double[] rotV = q.rotateVector(v);
                            cube[i].e[j].p[k].x = rotV[0];
                            cube[i].e[j].p[k].y = rotV[1];
                            cube[i].e[j].p[k].z = rotV[2];
                        }
                    }
                }
                break;
            case KeyEvent.VK_C:
                if(++axis==3) axis = 0;
                break;
        }
        repaint();
    }

    public static int[] dToIArr(double[] doubleArray) {
        int[] intArray = new int[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            intArray[i] = (int) doubleArray[i]; // Casting to int
        }
        return intArray;
    }
}
