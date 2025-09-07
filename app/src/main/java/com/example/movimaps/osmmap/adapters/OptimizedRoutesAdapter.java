package com.example.movimaps.osmmap.adapters;

public class OptimizedRoutesAdapter extends RecyclerView.Adapter<OptimizedRoutesAdapter.RouteViewHolder> {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private DriverPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_management);

        initViews();
        setupToolbar();
        setupViewPager();
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gestión de Choferes");
    }

    // NUEVO: Configurar ViewPager para choferes
    private void setupViewPager() {
        pagerAdapter = new DriverPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("👨‍💼 Choferes");
                    break;
                case 1:
                    tab.setText("🛣️ Rutas");
                    break;
                case 2:
                    tab.setText("🚏 Paradas");
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
