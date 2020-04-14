public class ScoreBoard extends javax.swing.JPanel {
    
    private int score;

    public ScoreBoard() {
        initComponents();
        myInit();
    }
    
    private void myInit() {
        score = 0;
    }
    
    public void incrementScore(int increment) {
        score += increment;
        lbl_score.setText(score+"");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblScore = new javax.swing.JLabel();
        lbl_score = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 255));
        setBorder(javax.swing.BorderFactory.createCompoundBorder());
        setForeground(new java.awt.Color(255, 255, 255));

        lblScore.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblScore.setText("Score");

        lbl_score.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel1.setText("Snake hecho por Alejandro Navarro 1ºDAM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblScore, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lbl_score, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblScore)
                .addComponent(lbl_score)
                .addComponent(jLabel1))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblScore;
    private javax.swing.JLabel lbl_score;
    // End of variables declaration//GEN-END:variables
}
