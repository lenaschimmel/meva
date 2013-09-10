//
//  StartViewController.m
//  BasisApp
//
//  Created by Tillmann Heigel on 03.09.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "StartViewController.h"
#import "LoginViewController.h"
#import "SBTableAlert.h"

@interface StartViewController (){
    UIButton *categoryButton;
    UITableView *mapsTableView;
}

@end

@implementation StartViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    [self addLoginButton];
    
    [self addCategoryButton];
    
    [self addMapsTableView];
    
    [self addConstraints];
}

- (void)addLoginButton{
    UIBarButtonItem *logInButton = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"login", @"login") style:UIBarButtonItemStylePlain target:self action:@selector(showLoginScreen)];
    self.navigationItem.rightBarButtonItem=logInButton;
}

- (void)showLoginScreen{
    LoginViewController *login = [[LoginViewController alloc] init];
    [self.navigationController presentViewController:login animated:YES completion:nil];
}

- (void)addCategoryButton{
    categoryButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [categoryButton setTitle:NSLocalizedString(@"Category...", @"Category...") forState:UIControlStateNormal];
    [categoryButton sizeToFit];
    categoryButton.translatesAutoresizingMaskIntoConstraints=NO;
    
    [categoryButton addTarget:self action:@selector(openCategoryTableAlert) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:categoryButton];
}

- (void)openCategoryTableAlert{
    SBTableAlert *alert = [[SBTableAlert alloc] initWithTitle:NSLocalizedString(@"Category", @"Category") cancelButtonTitle:NSLocalizedString(@"cancel", @"cancle") messageFormat:NSLocalizedString(@"Please choose a category", @"Please choose a category")];
    [alert setType:SBTableAlertTypeSingleSelect];
	[alert setDelegate:self];
	[alert setDataSource:self];
    [alert.view setTag:0];
	
	[alert show];
}

-(void)addMapsTableView{
    mapsTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 80, self.view.bounds.size.width, self.view.bounds.size.height-80) style:UITableViewStyleGrouped];
    [mapsTableView setDelegate:self];
    [mapsTableView setDataSource:self];
    
    [self.view addSubview:mapsTableView];
}

- (void)addConstraints{
    
////CATEGORY_BUTTON\\\\
//    
//    [self.view addConstraints:[NSLayoutConstraint
//                          constraintsWithVisualFormat:@"H:|-8-[tableView]-8-|"
//                          options:0 metrics:nil views:views]];
//    
    //category button center
//    NSLayoutConstraint *CategoryConstraint = [NSLayoutConstraint
//                                      constraintWithItem:categoryButton
//                                      attribute:NSLayoutAttributeCenterX
//                                      relatedBy:NSLayoutRelationEqual
//                                      toItem:self.view
//                                      attribute:NSLayoutAttributeCenterX
//                                      multiplier:1
//                                      constant:0];
//    
//    [self.view addConstraint:CategoryConstraint];

    //category button top
//    CategoryConstraint = [NSLayoutConstraint
//                  constraintWithItem:categoryButton
//                  attribute:NSLayoutAttributeTop
//                  relatedBy:NSLayoutRelationEqual
//                  toItem:self.view
//                  attribute:NSLayoutAttributeTop
//                  multiplier:1.0f
//                  constant:20.f];
//    
//    [self.view addConstraint:CategoryConstraint];

    //category button width
//    CategoryConstraint = [NSLayoutConstraint
//                  constraintWithItem:categoryButton
//                  attribute:NSLayoutAttributeWidth
//                  relatedBy:NSLayoutRelationEqual
//                  toItem:self.view
//                  attribute:NSLayoutAttributeWidth
//                  multiplier:1.0f
//                  constant:-35.f];
//    
//    [self.view addConstraint:CategoryConstraint];

    ////MAPS_TABLE_VIEW\\\\
    
    //maps tableview bottom
    NSLayoutConstraint* TableViewConstraint = [NSLayoutConstraint
                  constraintWithItem:mapsTableView
                  attribute:NSLayoutAttributeWidth
                  relatedBy:NSLayoutRelationEqual
                  toItem:self.view
                  attribute:NSLayoutAttributeWidth
                  multiplier:1.0f
                  constant:0.f];
    [self.view addConstraint:TableViewConstraint];
    
    //maps tableview bottom
//    TableViewConstraint = [NSLayoutConstraint
//                  constraintWithItem:mapsTableView
//                  attribute:NSLayoutAttributeBottom
//                  relatedBy:NSLayoutRelationEqual
//                  toItem:self.view
//                  attribute:NSLayoutAttributeBottom
//                  multiplier:1.0f
//                  constant:00.f];
//    [self.view addConstraint:TableViewConstraint];
    
    
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 2;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSLog(@"cellForRowAtIndexPath: %@",indexPath);
    
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"cell"];
    
    cell.selectionStyle = UITableViewCellSelectionStyleNone;

    cell.textLabel.text = @"map";
    
    return cell;
}      

- (UITableViewCell *)tableAlert:(SBTableAlert *)tableAlert cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	UITableViewCell *cell;
	
	if (tableAlert.view.tag == 0 || tableAlert.view.tag == 1) {
		cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
	} else {
		// Note: SBTableAlertCell
		cell = [[SBTableAlertCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
	}
	
	[cell.textLabel setText:[NSString stringWithFormat:@"Cell %d", indexPath.row]];
	
	return cell;
}

- (NSInteger)tableAlert:(SBTableAlert *)tableAlert numberOfRowsInSection:(NSInteger)section {
	if (tableAlert.type == SBTableAlertTypeSingleSelect)
		return 3;
	else
		return 10;
}

- (NSInteger)numberOfSectionsInTableAlert:(SBTableAlert *)tableAlert {
	if (tableAlert.view.tag == 3)
		return 2;
	else
		return 1;
}

- (NSString *)tableAlert:(SBTableAlert *)tableAlert titleForHeaderInSection:(NSInteger)section {
	if (tableAlert.view.tag == 3)
		return [NSString stringWithFormat:@"Section Header %d", section];
	else
		return nil;
}

#pragma mark - SBTableAlertDelegate

- (void)tableAlert:(SBTableAlert *)tableAlert didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	if (tableAlert.type == SBTableAlertTypeMultipleSelct) {
		UITableViewCell *cell = [tableAlert.tableView cellForRowAtIndexPath:indexPath];
		if (cell.accessoryType == UITableViewCellAccessoryNone)
			[cell setAccessoryType:UITableViewCellAccessoryCheckmark];
		else
			[cell setAccessoryType:UITableViewCellAccessoryNone];
		
		[tableAlert.tableView deselectRowAtIndexPath:indexPath animated:YES];
	}
}

- (void)tableAlert:(SBTableAlert *)tableAlert didDismissWithButtonIndex:(NSInteger)buttonIndex {
	NSLog(@"Dismissed: %i", buttonIndex);
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



@end
