//
//  LoginViewController.m
//  GUIDEAPP
//
//  Created by Tillmann Heigel on 03.07.13.
//  Copyright (c) 2013 PIMAR. All rights reserved.
//

#import "LoginViewController.h"

@interface LoginViewController ()<UITableViewDelegate,UITableViewDataSource,UITextFieldDelegate>{
    UITableView *loginTableView;
    UITableView *settingsTableView;
    UIButton *loginButton;
    UIButton *settingsButton;
    
    UITextField *usernameTextField;
    UITextField *passwordTextField;
    UITextField *httpAddressTextField;
    UITextField *portTextField;
    UITextField *textFieldToResign;
    
    NSUserDefaults *nsu;
}

@end

@implementation LoginViewController

BOOL showSettings = FALSE;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated{
    showSettings = FALSE;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    nsu = [NSUserDefaults standardUserDefaults];
        
    [self initBackground];
    
    [self initHeader];
    
    [self initLoginTableView];
    
    [self initLoginButton];
    
    [self initSettingsButton];
    
    [self initSettingsTableView];
    
    [self initGestureRecognizer];
    
}


-(void)initBackground{
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"key2guideBackground_smallLogo_center"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
}

#define HEADER 30
#define BORDER_TOP 20
#define SEPERATOR 15

#define BORDER_SIDE 30
#define BORDER_MID 10
#define MAX_WIDTH 320

-(void)initHeader{
    UILabel *header = [[UILabel alloc] initWithFrame:CGRectMake(BORDER_SIDE, BORDER_TOP, MAX_WIDTH - 2* BORDER_SIDE, HEADER)];
    header.text = NSLocalizedString(@"Please fill in your userdata!", @"Please fill in your userdata!");
    header.backgroundColor = [UIColor clearColor];
    header.textColor = [UIColor whiteColor];
    
    [self.view addSubview:header];
}


#define BUTTON_HEIGHT 44

-(void)initLoginTableView{
    loginTableView = [[UITableView alloc] initWithFrame:CGRectMake(0,HEADER + BORDER_TOP, MAX_WIDTH,100) style:UITableViewStyleGrouped];
    [loginTableView.backgroundView setAlpha:0];
    loginTableView.scrollEnabled = FALSE;
    [loginTableView setDelegate:self];
    [loginTableView setDataSource:self];
    
    [self.view addSubview:loginTableView];
}

-(void)initSettingsTableView{
    settingsTableView = [[UITableView alloc] initWithFrame:CGRectMake(0,HEADER + BORDER_TOP+170, MAX_WIDTH,200) style:UITableViewStyleGrouped];
    [settingsTableView.backgroundView setAlpha:0];
    settingsTableView.scrollEnabled = FALSE;
    [settingsTableView setDelegate:self];
    [settingsTableView setDataSource:self];
    
    
    [self.view addSubview:settingsTableView];
}

-(void)initSettingsButton{
    
    //alloc&init
    settingsButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    
    //apperance
    CGFloat x = BORDER_SIDE;
    CGFloat y = HEADER + BORDER_TOP + loginTableView.frame.size.height + SEPERATOR;
    CGFloat width = MAX_WIDTH / 2 - (BORDER_SIDE + BORDER_MID);
    CGFloat height = BUTTON_HEIGHT;
    [settingsButton setFrame:CGRectMake(x, y, width, height)];
    
    //text
    [settingsButton setTitle:NSLocalizedString(@"Settings", @"Settings")
                    forState:UIControlStateNormal];
    //action
    [settingsButton addTarget:self action:@selector(settings) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:settingsButton];
}
-(void)initLoginButton{
    
    //alloc&init
    loginButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    
    //apperance
    CGFloat x = MAX_WIDTH / 2 + BORDER_MID;
    CGFloat y = HEADER + BORDER_TOP + loginTableView.frame.size.height + SEPERATOR;
    CGFloat width = MAX_WIDTH / 2 - (BORDER_SIDE + BORDER_MID);
    CGFloat height = BUTTON_HEIGHT;
    [loginButton setFrame:CGRectMake(x, y, width, height)];
    
    //text
    [loginButton setTitle:NSLocalizedString(@"Login", @"Login") forState:UIControlStateNormal];
    
    //action
    [loginButton addTarget:self action:@selector(login) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:loginButton];
}

-(void)initGestureRecognizer{
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                   initWithTarget:self
                                   action:@selector(dismissKeyboard)];
    
    [self.view addGestureRecognizer:tap];
}

-(void)dismissKeyboard {
    [textFieldToResign resignFirstResponder];
}

- (void)login{/*
    App *project = [[App alloc] init];
    NSArray *projects = [[NSArray alloc] initWithObjects:project, nil];
    
    ProjectsOverviewViewController *projectsOverviewViewController = [[ProjectsOverviewViewController alloc] initWithProjects:projects AndLoginViewController:self];
    UINavigationController *projectsNavCon = [[UINavigationController alloc] initWithRootViewController:projectsOverviewViewController];
    projectsOverviewViewController.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
    [self presentViewController:projectsNavCon animated:YES completion:nil];
               */
}

- (void)settings{
    
    showSettings = showSettings?FALSE:TRUE;
    //TODO: animate
    
    
    
    
    [settingsTableView beginUpdates];
    
    if (showSettings) {
        NSIndexSet *indexSet = [NSIndexSet indexSetWithIndexesInRange:NSMakeRange(0, 2)];
        [settingsTableView insertSections:indexSet withRowAnimation:UITableViewRowAnimationAutomatic];
    } else {
        NSIndexSet *indexSet = [NSIndexSet indexSetWithIndexesInRange:NSMakeRange(0, 2)];
        [settingsTableView deleteSections:indexSet withRowAnimation:UITableViewRowAnimationAutomatic];
        [portTextField removeFromSuperview];
        [httpAddressTextField removeFromSuperview];
    }
    
    [settingsTableView endUpdates];
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 0;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 2;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"cell"];
    
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    if ([tableView isEqual:loginTableView]) {
        switch (indexPath.row) {
            case 0:
                return [self usernameCellBuilder:cell];
                break;
            case 1:
                return [self passwordCellBuilder:cell];
                break;
            default:
                break;
        }
    } else { //isEqual:settingsTableView
        switch (indexPath.section) {
            case 0:
                return [self httpCellBuilder:cell];
                break;
            case 1:
                return [self portCellBuilder:cell];
                break;
            default:
                break;
        }
    }
    
    
    return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    NSString *sectionName;
    if ([tableView isEqual:settingsTableView]) {
        switch (section) {
            case 0:
                sectionName =  NSLocalizedString(@"httpAddress", @"httpAddress");
                break;
            case 1:
                sectionName =  NSLocalizedString(@"port", @"port");
                break;
            default:
                break;
        }
    }
    
    return sectionName;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    if ([tableView isEqual:settingsTableView]) {
        
        UIView *sectionHeaderView = [[UIView alloc] initWithFrame:
                                     CGRectMake(0, 0, tableView.frame.size.width, 50.0)];
        sectionHeaderView.backgroundColor = [UIColor clearColor];
        
        UILabel *headerLabel = [[UILabel alloc] initWithFrame:
                                CGRectMake(15, 15, sectionHeaderView.frame.size.width, 20.0)];
        
        headerLabel.backgroundColor = [UIColor clearColor];
        headerLabel.textColor = [UIColor whiteColor];
        [sectionHeaderView addSubview:headerLabel];
        
        switch (section) {
            case 0:
                headerLabel.text =  NSLocalizedString(@"httpAddress", @"httpAddress");
                return sectionHeaderView;
                break;
            case 1:
                headerLabel.text = NSLocalizedString(@"port", @"port");
                return sectionHeaderView;
                break;
            default:
                break;
        }
        
        return sectionHeaderView;
    } else return nil;
}

#define USER_FIELD 0
#define PASS_FIELD 1
#define HTTP_FIELD 2
#define PORT_FIELD 3

-(UITableViewCell*)usernameCellBuilder:(UITableViewCell*)cell{
    //label
    CGFloat textFieldBorder = 10.f;
    usernameTextField = [[UITextField alloc] initWithFrame:CGRectMake(textFieldBorder, 9.f, CGRectGetWidth(cell.bounds)-(3*textFieldBorder), 31.f)];
    usernameTextField.tag = USER_FIELD;
    usernameTextField.returnKeyType = UIReturnKeyNext;
    usernameTextField.enablesReturnKeyAutomatically = YES;
    usernameTextField.autocorrectionType = UITextAutocorrectionTypeNo;
    usernameTextField.autocapitalizationType = UITextAutocapitalizationTypeNone;
    usernameTextField.delegate = self;
    [cell.contentView addSubview:usernameTextField];
    
    usernameTextField.textColor = [UIColor blackColor];
    usernameTextField.placeholder = NSLocalizedString(@"username", @"username");
    
    usernameTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    
    
    //get last used username
    [usernameTextField setText:[nsu stringForKey:@"username"]];
    
    return cell;
}


-(UITableViewCell*)passwordCellBuilder:(UITableViewCell*)cell{
    //label
    CGFloat textFieldBorder = 10.f;
    passwordTextField = [[UITextField alloc] initWithFrame:CGRectMake(textFieldBorder, 9.f, CGRectGetWidth(cell.bounds)-(3*textFieldBorder), 31.f)];
    passwordTextField.tag = PASS_FIELD;
    passwordTextField.returnKeyType = UIReturnKeyJoin;
    passwordTextField.secureTextEntry = YES;
    passwordTextField.enablesReturnKeyAutomatically = YES;
    passwordTextField.autocorrectionType = UITextAutocorrectionTypeNo;
    passwordTextField.autocapitalizationType = UITextAutocapitalizationTypeNone;
    passwordTextField.delegate = self;
    [cell.contentView addSubview:passwordTextField];
    
    passwordTextField.textColor = [UIColor blackColor];
    passwordTextField.placeholder = NSLocalizedString(@"password", @"password");
    
    passwordTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    
    //get last used password
    [passwordTextField setText:[nsu stringForKey:@"password"]];
    
    return cell;
}

#pragma mark - UITextField delegate

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField.tag == USER_FIELD) {
        [passwordTextField becomeFirstResponder];
        return NO;
    }
    if (textField.tag == PASS_FIELD) {
        [textField resignFirstResponder];
        [self login];
        return YES;
    }
    else{
        [textField resignFirstResponder];
        return YES;
    }
}

-(UITableViewCell*)httpCellBuilder:(UITableViewCell*)cell{
    //label
    CGFloat textFieldBorder = 10.f;
    httpAddressTextField = [[UITextField alloc] initWithFrame:CGRectMake(textFieldBorder, 9.f, CGRectGetWidth(cell.bounds)-(3*textFieldBorder), 31.f)];
    httpAddressTextField.tag = HTTP_FIELD;
    httpAddressTextField.returnKeyType = UIReturnKeyNext;
    httpAddressTextField.enablesReturnKeyAutomatically = YES;
    httpAddressTextField.autocorrectionType = UITextAutocorrectionTypeNo;
    httpAddressTextField.autocapitalizationType = UITextAutocapitalizationTypeNone;
    httpAddressTextField.delegate = self;
    [cell.contentView addSubview:httpAddressTextField];
    
    httpAddressTextField.textColor = [UIColor blackColor];
    httpAddressTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    
    
    //get last used username
    [httpAddressTextField setText:[nsu stringForKey:@"httpAddress"]];
    
    return cell;
}

-(UITableViewCell*)portCellBuilder:(UITableViewCell*)cell{
    //label
    CGFloat textFieldBorder = 10.f;
    portTextField = [[UITextField alloc] initWithFrame:CGRectMake(textFieldBorder, 9.f, CGRectGetWidth(cell.bounds)-(3*textFieldBorder), 31.f)];
    portTextField.tag = PORT_FIELD;
    portTextField.returnKeyType = UIReturnKeyNext;
    portTextField.enablesReturnKeyAutomatically = YES;
    portTextField.autocorrectionType = UITextAutocorrectionTypeNo;
    portTextField.autocapitalizationType = UITextAutocapitalizationTypeNone;
    portTextField.delegate = self;
    [cell.contentView addSubview:portTextField];
    
    portTextField.textColor = [UIColor blackColor];
    portTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    
    
    //get last used username
    [portTextField setText:[nsu stringForKey:@"port"]];
    
    return cell;
}

- (void)textFieldDidBeginEditing:(UITextField *)textField {
    textFieldToResign = textField;
    
    //resize
    self.view.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y,self.view.frame.size.width, self.view.frame.size.height - 215 + 50);
    
    // go to right position
}

-(void)textFieldDidEndEditing:(UITextField *)textField{
    [nsu setValue:usernameTextField.text forKey:@"username"];
    [nsu setValue:passwordTextField.text forKey:@"password"];
    [nsu setValue:httpAddressTextField.text forKey:@"httpAddress"];
    [nsu setValue:portTextField.text forKey:@"port"];
    
    //keyboard will hide
    self.view.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y,self.view.frame.size.width, self.view.frame.size.height + 215 - 50);
    
    //resize
}


-(BOOL)textFieldShouldClear:(UITextField*)textField
{
    if ([textField isEqual:usernameTextField]) {
        usernameTextField.text = nil;
        passwordTextField.text = nil;
    }
    
    return YES;
}

@end
